package com.server.talkster.services;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.server.talkster.models.FCMToken;
import com.server.talkster.models.MessageNotification;
import com.server.talkster.models.PrivateChatMessage;
import com.server.talkster.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class FirebaseMessagingService
{

    private final UserService userService;
    private final TokenService tokenService;
    private final FirebaseMessaging firebaseMessaging;

    @Autowired
    public FirebaseMessagingService(FirebaseMessaging firebaseMessaging, TokenService tokenService, UserService userService)
    {
        this.userService = userService;
        this.tokenService = tokenService;
        this.firebaseMessaging = firebaseMessaging;
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

        try { firebaseMessaging.send(message); }
        catch (FirebaseMessagingException e){ System.out.println(e.getMessage()); }
    }

    public void sendMessageNotification(PrivateChatMessage message, long receiverID, long senderID)
    {
        Map<String, String> data = new HashMap<>();
        User user = userService.findUserByID(senderID);
        List<FCMToken> tokens = tokenService.findAllByOwnerID(receiverID);

        String fullName = user.getFullName();
        data.put("senderId", Long.toString(senderID));

        for (FCMToken entry: tokens)
        {
            String token = entry.getToken();
            String content = message.getMessageContent();
            MessageNotification note = new MessageNotification(fullName, content, data);

            try { sendNotification(note, token); }
            catch (FirebaseMessagingException e) { tokenService.removeAllByToken(token); }
        }
    }
}

