package com.server.talkster.controllers;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.server.talkster.dto.UserJWT;
import com.server.talkster.dto.AuthenticationDTO;
import com.server.talkster.dto.RegistrationDTO;
import com.server.talkster.dto.VerifiedUserDTO;
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
    public ResponseEntity<UserJWT> findUserByMail(@RequestBody @Validated AuthenticationDTO authenticationDTO, BindingResult bindingResult)
    {

        String mail = authenticationDTO.getMail();
        Optional<User> findUser = userService.findByMail(mail);

        return findUser.map(user -> {

            UserJWT userJWT = new UserJWT();

            userJWT.setID(user.getId());
            createAuthPin(user.getId(), mail);
            userJWT.setAccessToken(jwtUtil.generateJWTToken(user.getId(), false));

            return ResponseEntity.ok(userJWT);


        }).orElseGet(() -> {
            UserJWT userJWT = new UserJWT();

            userJWT.setID(-1);
            createAuthPin(-1, mail);
            userJWT.setAccessToken(jwtUtil.generateJWTToken(-1L, false));

            return new ResponseEntity<>(userJWT, HttpStatus.NOT_FOUND);
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
            UserJWT userJWT = new UserJWT();

            userJWT.setID(userID);

            if(userID == -1)
            {
                userJWT.setAccessToken(headers.get("authorization"));
                return new ResponseEntity<>(userJWT, HttpStatus.ACCEPTED);
            }

            VerifiedUserDTO verifiedUserDTO = new VerifiedUserDTO();

            User user = userService.findUserByID(userID);
            userJWT.setAccessToken(jwtUtil.generateJWTToken(userID, true));

            verifiedUserDTO.setUser(user);
            verifiedUserDTO.setUserJWT(userJWT);

            return ResponseEntity.ok(verifiedUserDTO);
        }
        return new ResponseEntity<>(authenticationDTO, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/register")
    public ResponseEntity<VerifiedUserDTO> registerUser(@RequestHeader Map<String, String> headers, @RequestBody @Validated RegistrationDTO registrationDTO)
    {
        User user;
        long ID = -1;

        DecodedJWT token = jwtUtil.validateToken(headers.get("authorization"));

        user = userService.convertToUser(registrationDTO);
        user = userService.createUser(user);
        ID = user.getId();

        UserJWT userJWT = new UserJWT();
        VerifiedUserDTO verifiedUserDTO = new VerifiedUserDTO();

        userJWT.setID(ID);
        userJWT.setAccessToken(jwtUtil.generateJWTToken(ID, true));

        verifiedUserDTO.setUser(user);
        verifiedUserDTO.setUserJWT(userJWT);

        return ResponseEntity.ok(verifiedUserDTO);
    }

    @PostMapping("/verify-session")
    public ResponseEntity<VerifiedUserDTO> verifySession(@RequestHeader Map<String, String> headers, @RequestBody @Validated UserJWT userJWT)
    {
        DecodedJWT token = jwtUtil.validateToken(headers.get("authorization"));

        if(jwtUtil.getIDFromToken(token) == userJWT.getID() || Objects.equals(headers.get("authorization"), userJWT.getAccessToken()))
        {
            User user = userService.findUserByID(userJWT.getID());

            if(user == null)
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

            VerifiedUserDTO verifiedUserDTO = new VerifiedUserDTO();

            verifiedUserDTO.setUser(user);
            verifiedUserDTO.setUserJWT(userJWT);

            return ResponseEntity.ok(verifiedUserDTO);
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
