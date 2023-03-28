package com.server.talkster.controllers;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.server.talkster.models.NotificationMessage;
import com.server.talkster.security.JWTUtil;
import com.server.talkster.services.ChatService;
import com.server.talkster.services.FirebaseMessagingService;
import com.server.talkster.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {

    private final FirebaseMessagingService firebaseMessagingService;

    @Autowired
    public NotificationController(FirebaseMessagingService firebaseMessagingService)
    {
        this.firebaseMessagingService = firebaseMessagingService;
    }

    @GetMapping("/send")
    public String sendNotification(@RequestBody NotificationMessage note,
                                   @RequestParam String token) throws FirebaseMessagingException {
        return firebaseMessagingService.sendNotification(note, token);
    }
}
