package com.server.talkster.services;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.server.talkster.models.NotificationMessage;
import com.server.talkster.repositories.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FirebaseMessagingService {

    private final FirebaseMessaging firebaseMessaging;

    @Autowired
    public FirebaseMessagingService(FirebaseMessaging firebaseMessaging) { this.firebaseMessaging = firebaseMessaging; }


    public String sendNotification(NotificationMessage note, String token) throws FirebaseMessagingException {

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
            return "Success";
        }
        catch (FirebaseMessagingException e){
            e.printStackTrace();
            return "Failure";
        }
    }

}

