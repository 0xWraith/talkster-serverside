package com.server.talkster.controllers;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.server.talkster.dto.MessageDTO;
import com.server.talkster.models.Chat;
import com.server.talkster.models.Message;
import com.server.talkster.security.JWTUtil;
import com.server.talkster.services.ChatService;
import com.server.talkster.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatController
{

    private final JWTUtil jwtUtil;
    private final ChatService chatService;
    private final MessageService messageService;

    @Autowired
    public ChatController(JWTUtil jwtUtil, ChatService chatService, MessageService messageService)
    {
        this.jwtUtil = jwtUtil;
        this.chatService = chatService;
        this.messageService = messageService;
    }

    @GetMapping("/")
    public ResponseEntity<String> chatIndex()
    {
        return ResponseEntity.ok("Chat index");
    }

    @GetMapping("/user-chats")
    public ResponseEntity<List<Chat>> findAllUserChats(@RequestHeader Map<String, String> headers)
    {
        DecodedJWT jwt = jwtUtil.checkJWTFromHeader(headers);

        if(jwt == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        List<Chat> chats = chatService.findAllByOwnerID(jwtUtil.getIDFromToken(jwt));
        chats.forEach(chat -> chat.setMessages(messageService.findAllByChatID(chat.getID())));

        return ResponseEntity.ok(chats);
    }

    @GetMapping("/find-chat/{chatID}/{ownerID}")
    public ResponseEntity<Chat> findChat(@RequestHeader Map<String, String> headers, @PathVariable long chatID, @PathVariable long ownerID)
    {
        DecodedJWT jwt = jwtUtil.checkJWTFromHeader(headers);

        if(jwt == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Chat chat = chatService.findUserChat(chatID, ownerID);

        if(chat != null)
            chat.setMessages(messageService.findAllByChatID(chat.getID()));
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok(chat);
    }

    @PostMapping("/send-message")
    public ResponseEntity<List<MessageDTO>> sendMessageToUser(@RequestHeader Map<String, String> headers, @RequestBody MessageDTO messageDTO)
    {
        DecodedJWT jwt = jwtUtil.checkJWTFromHeader(headers);

        if(jwt == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();


        long senderID = messageDTO.getsenderid();
        long receiverID = messageDTO.getreceiverid();

        Message senderMessage = messageService.convertToMessage(messageDTO);
        Message receiverMessage = messageService.convertToMessage(messageDTO);

        Chat senderChat = chatService.findByOwnerIDAndReceiverID(senderID, receiverID);
        Chat receiverChat = chatService.findByOwnerIDAndReceiverID(receiverID, senderID);

        if(senderChat == null)
        {
            senderChat = new Chat();
            senderChat.setOwnerID(senderID);
            senderChat.setReceiverID(receiverID);
        }

        if(receiverChat == null)
        {
            receiverChat = new Chat();
            receiverChat.setOwnerID(receiverID);
            receiverChat.setReceiverID(senderID);
        }
        senderChat.setUpdatedAt(OffsetDateTime.now().toString());
        receiverChat.setUpdatedAt(OffsetDateTime.now().toString());

        senderChat = chatService.save(senderChat);
        senderMessage.setChatID(senderChat.getID());
        messageService.saveMessage(senderMessage);

        if(receiverID != senderID)
        {
            receiverChat = chatService.save(receiverChat);
            receiverMessage.setChatID(receiverChat.getID());
            messageService.saveMessage(receiverMessage);
        }
        else
            receiverMessage = senderMessage;

        MessageDTO senderDTO = messageService.convertToMessageDTO(senderMessage);
        MessageDTO receiverDTO = messageService.convertToMessageDTO(receiverMessage);

        return ResponseEntity.ok(new ArrayList<>(List.of(senderDTO, receiverDTO)));
    }

}
