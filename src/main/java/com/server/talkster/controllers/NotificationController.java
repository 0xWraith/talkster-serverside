package com.server.talkster.controllers;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.server.talkster.dto.TokenDTO;
import com.server.talkster.models.*;
import com.server.talkster.security.JWTUtil;
import com.server.talkster.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {

    private final JWTUtil jwtUtil;
    private final TokenService tokenService;

    @Autowired
    public NotificationController(JWTUtil jwtUtil, TokenService tokenService)
    {
        this.jwtUtil = jwtUtil;
        this.tokenService = tokenService;
    }

    @PutMapping("/add-token")
    public ResponseEntity<String> addToken(@RequestHeader Map<String, String> headers, @RequestBody TokenDTO tokenDTO)
    {
        DecodedJWT jwt = jwtUtil.checkJWTFromHeader(headers);

        if(jwt == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        String token = tokenDTO.getToken();
        long userID = jwtUtil.getIDFromToken(jwt);

        FCMToken fcmToken = tokenService.findFirstByToken(token);

        if (fcmToken == null)
            fcmToken = new FCMToken(userID, token);

        else
        {
            fcmToken.setOwnerID(userID);
            fcmToken.setUpdatedAt();
        }

        tokenService.saveToken(fcmToken);
        return ResponseEntity.ok("Token added");
    }
}
