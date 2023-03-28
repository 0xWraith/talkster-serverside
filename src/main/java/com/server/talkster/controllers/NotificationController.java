package com.server.talkster.controllers;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.server.talkster.dto.TokenDTO;
import com.server.talkster.models.*;
import com.server.talkster.security.JWTUtil;
import com.server.talkster.services.FirebaseMessagingService;
import com.server.talkster.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {

    private final FirebaseMessagingService firebaseMessagingService;
    private final UserService userService;
    private final JWTUtil jwtUtil;

    @Autowired
    public NotificationController(JWTUtil jwtUtil, FirebaseMessagingService firebaseMessagingService, UserService userService)
    {
        this.jwtUtil = jwtUtil;
        this.firebaseMessagingService = firebaseMessagingService;
        this.userService = userService;
    }

    @PutMapping("/add-token")
    public ResponseEntity<String> addToken(@RequestHeader Map<String, String> headers, @RequestBody TokenDTO tokenDTO){
        DecodedJWT jwt = jwtUtil.checkJWTFromHeader(headers);

        if(jwt == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        long userID = jwtUtil.getIDFromToken(jwt);
        String token = tokenDTO.getToken();
        FCMToken fcmToken = firebaseMessagingService.findFirstByToken(token);
        if (fcmToken != null) {
            fcmToken.setOwnerID(userID);
            fcmToken.setUpdatedAt();
        } else {
            fcmToken = new FCMToken(userID, token);
        }
        fcmToken = firebaseMessagingService.save(fcmToken);
        return ResponseEntity.ok("Token added");
    }
}
