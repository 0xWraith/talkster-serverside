package com.server.talkster.controllers;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.server.talkster.dto.AuthInfoDTO;
import com.server.talkster.dto.AuthenticationDTO;
import com.server.talkster.dto.RegistrationDTO;
import com.server.talkster.models.AuthKey;
import com.server.talkster.models.User;
import com.server.talkster.security.JWTUtil;
import com.server.talkster.services.AuthKeyService;
import com.server.talkster.services.MailSenderService;
import com.server.talkster.services.UserService;
import com.server.talkster.utility.exceptions.User.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController
{
    private final JWTUtil jwtUtil;
    private final UserService userService;
    private final AuthKeyService authKeyService;
    private final MailSenderService mailSenderService;

    @Autowired
    public AuthController(UserService userService, JWTUtil jwtUtil, AuthKeyService authKeyService, MailSenderService mailSenderService)
    {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.authKeyService = authKeyService;
        this.mailSenderService = mailSenderService;
    }

    @PostMapping("/find-user")
    public ResponseEntity<AuthInfoDTO> findUserByMail(@RequestBody @Validated AuthenticationDTO authenticationDTO, BindingResult bindingResult)
    {

        String mail = authenticationDTO.getMail();
        Optional<User> findUser = userService.findByMail(mail);

        return findUser.map(user -> {

            AuthInfoDTO authInfoDTO = new AuthInfoDTO();

            authInfoDTO.setID(user.getId());
            createAuthPin(user.getId(), mail);
            authInfoDTO.setJWTToken(jwtUtil.generateJWTToken(user.getId(), false));

            return ResponseEntity.ok(authInfoDTO);


        }).orElseGet(() -> {
            AuthInfoDTO authInfoDTO = new AuthInfoDTO();

            authInfoDTO.setID(-1);
            createAuthPin(-1, mail);
            authInfoDTO.setJWTToken(jwtUtil.generateJWTToken(-1L, false));

            return new ResponseEntity<>(authInfoDTO, HttpStatus.NOT_FOUND);
        });
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyLoginKey(@RequestHeader Map<String, String> headers, @RequestBody @Validated AuthenticationDTO authenticationDTO, BindingResult bindingResult)
    {
        DecodedJWT token = jwtUtil.validateToken(headers.get("authorization"));

        long userID = jwtUtil.getIDFromToken(token);
        AuthKey authKey = authKeyService.findKey(authenticationDTO.getMail(), userID);

        if(authKey == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);


        if(Objects.equals(authKey.getCode(), authenticationDTO.getCode()) && Objects.equals(authKey.getownerid(), userID))
        {
            AuthInfoDTO authInfoDTO = new AuthInfoDTO();
            authInfoDTO.setID(userID);

            if(userID == -1)
            {
                authInfoDTO.setJWTToken(headers.get("authorization"));
                return new ResponseEntity<>(authInfoDTO, HttpStatus.ACCEPTED);
            }

            authInfoDTO.setJWTToken(jwtUtil.generateJWTToken(userID, true));

            return ResponseEntity.ok(authInfoDTO);
        }
        return new ResponseEntity<>(authenticationDTO, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthInfoDTO> registerUser(@RequestHeader Map<String, String> headers, @RequestBody @Validated RegistrationDTO registrationDTO)
    {
        User user;
        DecodedJWT token = jwtUtil.validateToken(headers.get("authorization"));

        user = userService.convertToUser(registrationDTO);
        long ID = userService.createUser(user);

        AuthInfoDTO authInfoDTO = new AuthInfoDTO();

        authInfoDTO.setID(ID);
        authInfoDTO.setJWTToken(jwtUtil.generateJWTToken(ID, true));

        return ResponseEntity.ok(authInfoDTO);
    }

    @PostMapping("/verify-session")
    public ResponseEntity<?> verifySession(@RequestHeader Map<String, String> headers, @RequestBody @Validated AuthInfoDTO authInfoDTO)
    {
        DecodedJWT token = jwtUtil.validateToken(headers.get("authorization"));

        if(jwtUtil.getIDFromToken(token) == authInfoDTO.getID() || Objects.equals(headers.get("authorization"), authInfoDTO.getJWTToken()))
        {
            User user = userService.findUserByID(authInfoDTO.getID());
            return (user == null ? new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED) : ResponseEntity.ok(authInfoDTO));
        }

        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
    private void createAuthPin(long ownerID, String mail)
    {
        String nums = "0123456789";
        Random rand = new Random();
        StringBuilder key = new StringBuilder(5);

        for (int i = 0; i < 5; i++)
            key.append(nums.charAt(rand.nextInt(nums.length())));

        authKeyService.createAuthKey(ownerID, mail, key.toString());
        mailSenderService.sendMail("Talkster key: " + key, "Talkster", mail);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> exceptionHandleUserNotFound(UserNotFoundException userNotFoundException)
    {
        return new ResponseEntity<String>("fesfsfs", HttpStatus.BAD_REQUEST);
    }
}
