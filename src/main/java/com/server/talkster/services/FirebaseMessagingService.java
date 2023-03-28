package com.server.talkster.services;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.server.talkster.models.Chat;
import com.server.talkster.models.FCMToken;
import com.server.talkster.models.MessageNotification;
import com.server.talkster.repositories.TokenRepository;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FirebaseMessagingService {

    private final FirebaseMessaging firebaseMessaging;
    private final TokenRepository tokenRepository;

    @Autowired
    public FirebaseMessagingService(FirebaseMessaging firebaseMessaging, TokenRepository tokenRepository) {
        this.firebaseMessaging = firebaseMessaging;
        this.tokenRepository = tokenRepository;
    }


    public ResponseEntity<String> sendNotification(MessageNotification note, String token) throws FirebaseMessagingException {

        Notification notification = Notification
                .builder()
                .setTitle(note.getSubject())
                .setBody(note.getContent())
                .build();

        Message message = Message
                .builder()
                .setToken(token)
                .setNotification(notification)
                .putAllData(note.getData())
                .build();

        try {
            this.firebaseMessaging.send(message);
            return ResponseEntity.ok("Success");
        }
        catch (FirebaseMessagingException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
        }
    }

    public List<FCMToken> findAllByOwnerID(Long ownerID) { return tokenRepository.findAllByOwnerID(ownerID); }

    public FCMToken save(FCMToken fcmToken) { return tokenRepository.save(fcmToken);}
}

