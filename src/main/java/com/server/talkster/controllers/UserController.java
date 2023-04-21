package com.server.talkster.controllers;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.server.talkster.dto.NameDTO;
import com.server.talkster.dto.RegistrationDTO;
import com.server.talkster.models.FileReference;
import com.server.talkster.models.User;
import com.server.talkster.security.JWTUtil;
import com.server.talkster.services.FileService;
import com.server.talkster.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final JWTUtil jwtUtil;
    private final UserService userService;

    @Autowired
    public UserController(JWTUtil jwtUtil, UserService userService){
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PutMapping("/set-name")
    public ResponseEntity<?> uploadProfile(@RequestHeader Map<String, String> headers, @RequestBody NameDTO nameDTO){
        DecodedJWT jwt = jwtUtil.checkJWTFromHeader(headers);
        if(jwt == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        long userID = jwtUtil.getIDFromToken(jwt);
        User user = userService.findUserByID(userID);

        user.setFirstname(nameDTO.getFirstName());
        user.setLastname(nameDTO.getLastName());
        user = userService.createUser(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get-name/{id}")
    public ResponseEntity<NameDTO> uploadProfile(@RequestHeader Map<String, String> headers, @PathVariable("id") long userID){
        DecodedJWT jwt = jwtUtil.checkJWTFromHeader(headers);
        if(jwt == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        User user = userService.findUserByID(userID);
        NameDTO nameDTO = new NameDTO();
        nameDTO.setFirstName(user.getFirstname());
        nameDTO.setLastName(user.getLastname());

        return ResponseEntity.ok(nameDTO);
    }


}
