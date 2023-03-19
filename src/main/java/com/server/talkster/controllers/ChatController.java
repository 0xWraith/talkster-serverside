package com.server.talkster.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController
{
    /*private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public ChatController(SimpMessagingTemplate simpMessagingTemplate) { this.simpMessagingTemplate = simpMessagingTemplate; }

    @MessageMapping("/message") //app/message
    @SendTo("/chatroom/public")
    private MessageDTO receivePublicMessage(@Payload MessageDTO message)
    {
        System.out.println(message);
        return message;
    }

    @MessageMapping("/private-message")
    public MessageDTO receivePrivateMessage(@Payload MessageDTO messageDTO)
    {
        simpMessagingTemplate.convertAndSendToUser(messageDTO.getReceivername(), "/private", messageDTO); //user/David/private
        return messageDTO;
    }*/
}
