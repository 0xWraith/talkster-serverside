package com.server.talkster.controllers;

import com.server.talkster.dto.AuthInfoDTO;
import com.server.talkster.dto.AuthenticationDTO;
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
        Optional<User> findUser = userService.findByMail(authenticationDTO.getMail());

        return findUser.map(user -> {


            AuthInfoDTO authInfoDTO = new AuthInfoDTO();
            createAuthPin(user.getId(), mail);
            authInfoDTO.setJWTToken(jwtUtil.generateJWTToken(user.getId(), false));
            return ResponseEntity.ok(authInfoDTO);


        }).orElseGet(() -> {
            return new ResponseEntity<AuthInfoDTO>(new AuthInfoDTO(), HttpStatus.NOT_FOUND);
        });
    }

    private void createAuthPin(Long ownerID, String mail)
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
