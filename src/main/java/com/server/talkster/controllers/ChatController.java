package com.server.talkster.controllers;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.server.talkster.dto.ChatCreateDTO;
import com.server.talkster.dto.MessageDTO;
import com.server.talkster.models.Chat;
import com.server.talkster.models.Message;
import com.server.talkster.models.User;
import com.server.talkster.security.JWTUtil;
import com.server.talkster.services.ChatService;
import com.server.talkster.services.FirebaseMessagingService;
import com.server.talkster.services.MessageService;
import com.server.talkster.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatController
{

    private final JWTUtil jwtUtil;
    private final ChatService chatService;
    private final MessageService messageService;
    private final UserService userService;
    private final FirebaseMessagingService firebaseMessagingService;

    @Autowired
    public ChatController(JWTUtil jwtUtil, ChatService chatService, MessageService messageService, FirebaseMessagingService firebaseMessagingService, UserService userService)
    {
        this.jwtUtil = jwtUtil;
        this.chatService = chatService;
        this.messageService = messageService;
        this.firebaseMessagingService = firebaseMessagingService;
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<String> chatIndex()
    {
        return ResponseEntity.ok("Chat index");
    }

    @GetMapping("/user-chats-messages")
    public ResponseEntity<List<Chat>> findAllUserChats(@RequestHeader Map<String, String> headers)
    {
        System.out.println("Find all user chats with messages");
        DecodedJWT jwt = jwtUtil.checkJWTFromHeader(headers);

        if(jwt == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        List<Chat> chats = chatService.findAllByOwnerID(jwtUtil.getIDFromToken(jwt));
        chats.forEach(chat -> chat.setMessages(messageService.findAllByChatID(chat.getID())));

        return ResponseEntity.ok(chats);
    }

    @GetMapping("/user-chat/{receiverID}")
    public ResponseEntity<Chat> findAllUserChats(@RequestHeader Map<String, String> headers,@PathVariable("receiverID") long receiverID)
    {
        System.out.println("Find user chat");
        DecodedJWT jwt = jwtUtil.checkJWTFromHeader(headers);

        if(jwt == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        User receiver = userService.findUserByID(receiverID);
        if(receiver==null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Chat chat = chatService.findByOwnerIDAndReceiverID(jwtUtil.getIDFromToken(jwt),receiverID);
        chat.setMessages(messageService.findAllByChatID(chat.getID()));
        chat.setReceiverFirstname(receiver.getFirstname());
        chat.setReceiverLastname(receiver.getLastname());

        return ResponseEntity.ok(chat);
    }

    @GetMapping("/user-chats")
    public ResponseEntity<List<Chat>> getChatsInfo(@RequestHeader Map<String, String> headers) {
        System.out.println("Find all user chats");
        DecodedJWT jwt = jwtUtil.checkJWTFromHeader(headers);

        if(jwt == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        List<Chat> chats = chatService.findAllByOwnerID(jwtUtil.getIDFromToken(jwt));
        chats.forEach(chat -> {
            User receiver = userService.findUserByID(chat.getReceiverID());
            chat.setReceiverFirstname(receiver.getFirstname());
            chat.setReceiverLastname(receiver.getLastname());
        });

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

        System.out.println(chat.getMessages());
        System.out.println(ResponseEntity.ok(chat).getBody());

        return ResponseEntity.ok(chat);
    }

    @PostMapping("/create-chat")
    public ResponseEntity<Chat> createChat(@RequestHeader Map<String, String> headers, @RequestBody ChatCreateDTO chatCreateDTO){
        DecodedJWT jwt = jwtUtil.checkJWTFromHeader(headers);

        System.out.println("Create user chat");

        if(jwt == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();


        String receiverEmail = chatCreateDTO.getReceiverEmail();
        Optional<User> receiver = userService.findByMail(receiverEmail);
        if (receiver.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        long senderID = chatCreateDTO.getSenderID();
        long receiverID = receiver.get().getId();

        Chat senderChat = chatService.findByOwnerIDAndReceiverID(senderID, receiverID);
        Chat receiverChat = chatService.findByOwnerIDAndReceiverID(receiverID, senderID);

        if (senderChat != null && receiverChat != null)
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        if(senderChat == null)
        {
            senderChat = new Chat();
            senderChat.setOwnerID(senderID);
            senderChat.setReceiverID(receiverID);
            senderChat = chatService.save(senderChat);
        }

        if(receiverChat == null && receiverID != senderID)
        {
            receiverChat = new Chat();
            receiverChat.setOwnerID(receiverID);
            receiverChat.setReceiverID(senderID);
            receiverChat = chatService.save(receiverChat);
        }

        senderChat.setReceiverFirstname(receiver.get().getFirstname());
        senderChat.setReceiverLastname(receiver.get().getLastname());
        senderChat.setMessages(new ArrayList<>());
        return ResponseEntity.ok(senderChat);
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

        senderChat = chatService.save(senderChat);

        System.out.println(senderChat.getUpdatedAt());

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

        // Send notification
        firebaseMessagingService.sendMessageNotification(senderMessage, receiverID, senderID);

        return ResponseEntity.ok(new ArrayList<>(List.of(senderDTO, receiverDTO)));
    }

}
