package com.server.talkster.services;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.server.talkster.models.Chat;
import com.server.talkster.models.FCMToken;
import com.server.talkster.models.MessageNotification;
import com.server.talkster.models.User;
import com.server.talkster.repositories.TokenRepository;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class FirebaseMessagingService {

    private final FirebaseMessaging firebaseMessaging;
    private final TokenRepository tokenRepository;
    private final UserService userService;

    @Autowired
    public FirebaseMessagingService(FirebaseMessaging firebaseMessaging, TokenRepository tokenRepository, UserService userService) {
        this.firebaseMessaging = firebaseMessaging;
        this.tokenRepository = tokenRepository;
        this.userService = userService;
    }


    private void sendNotification(MessageNotification note, String token) throws FirebaseMessagingException {

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
        }
        catch (FirebaseMessagingException e){
            throw e;
        }
    }

    public void sendMessageNotification(com.server.talkster.models.Message message, long receiverID, long senderID){
        List<FCMToken> tokens = findAllByOwnerID(receiverID);
        User user = userService.findUserByID(receiverID);
        String firstName = user.getFirstname();
        Map<String, String> data = new HashMap<String,String>() {{
            put("senderId", Long.toString(senderID));
        }};

        for (FCMToken entry:tokens) {
            String token = entry.getToken();
            String content = message.getMessageContent();
            MessageNotification note = new MessageNotification(firstName, content, data);
            try {
                sendNotification(note, token);
            } catch (FirebaseMessagingException e){
                System.out.println("Deleting invalid token: " + token);
                removeAllByToken(token);
            }
        }
    }

    public List<FCMToken> findAllByOwnerID(Long ownerID) { return tokenRepository.findAllByOwnerID(ownerID); }
    public FCMToken findFirstByToken(String token) { return tokenRepository.findFirstByToken(token); }
    private void removeAllByToken(String token) {tokenRepository.removeAllByToken(token);}
    public FCMToken save(FCMToken fcmToken) { return tokenRepository.save(fcmToken);}
}

