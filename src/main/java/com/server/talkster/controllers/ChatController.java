package com.server.talkster.controllers;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.server.talkster.dto.*;
import com.server.talkster.models.*;
import com.server.talkster.security.JWTUtil;
import com.server.talkster.services.*;
import com.server.talkster.services.chat.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
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
    private final UserService userService;
    private final GroupService groupService;
    private final MessageService messageService;
    private final GroupMemberService groupMemberService;
    private final GroupChatMessageService groupChatMessageService;
    private final FirebaseMessagingService firebaseMessagingService;

    @Autowired
    public ChatController(JWTUtil jwtUtil, GroupChatMessageService groupChatMessageService, GroupMemberService groupMemberService, GroupService groupService, ChatService chatService, MessageService messageService, FirebaseMessagingService firebaseMessagingService, UserService userService)
    {
        this.jwtUtil = jwtUtil;
        this.chatService = chatService;
        this.userService = userService;
        this.groupService = groupService;
        this.messageService = messageService;
        this.groupMemberService = groupMemberService;
        this.groupChatMessageService = groupChatMessageService;
        this.firebaseMessagingService = firebaseMessagingService;
    }

    @GetMapping("/")
    public ResponseEntity<String> chatIndex()
    {
        return ResponseEntity.ok("Chat index");
    }

    @GetMapping("/user-chats-messages")
    public ResponseEntity<UserChatsDTO> findAllUserChats(@RequestHeader Map<String, String> headers)
    {
        System.out.println("Find all user chats with messages");
        DecodedJWT jwt = jwtUtil.checkJWTFromHeader(headers);

        if(jwt == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        UserChatsDTO userChatsDTO = new UserChatsDTO();

        ArrayList<PrivateChat> chats = new ArrayList<>(chatService.findAllByOwnerID(jwtUtil.getIDFromToken(jwt)));
        ArrayList<GroupChat> groups = new ArrayList<>(groupService.findAllGroupsByUserId(jwtUtil.getIDFromToken(jwt)));

        chats.forEach(chat -> chat.setMessages(messageService.findAllByChatID(chat.getID())));
        groups.forEach(group -> group.setMessages(groupChatMessageService.findAllByChatID(group.getId())));
        groups.forEach(group -> group.setGroupMembers(groupMemberService.findAllGroupMembers(group.getId())));

        userChatsDTO.setGroupChats(groups);
        userChatsDTO.setPrivateChats(chats);

        System.out.println(userChatsDTO);

        return ResponseEntity.ok(userChatsDTO);
    }

    @GetMapping("/user-chat/{receiverID}")
    public ResponseEntity<PrivateChat> findAllUserChats(@RequestHeader Map<String, String> headers, @PathVariable("receiverID") long receiverID)
    {
        System.out.println("Find user chat");
        DecodedJWT jwt = jwtUtil.checkJWTFromHeader(headers);

        if(jwt == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        User receiver = userService.findUserByID(receiverID);
        if(receiver==null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        PrivateChat chat = chatService.findByOwnerIDAndReceiverID(jwtUtil.getIDFromToken(jwt),receiverID);
        chat.setMessages(messageService.findAllByChatID(chat.getID()));
        chat.setReceiverFirstname(receiver.getFirstname());
        chat.setReceiverLastname(receiver.getLastname());

        return ResponseEntity.ok(chat);
    }

    @GetMapping("/user-chats")
    public ResponseEntity<List<PrivateChat>> getChatsInfo(@RequestHeader Map<String, String> headers)
    {
        System.out.println("Find all user chats");
        DecodedJWT jwt = jwtUtil.checkJWTFromHeader(headers);

        if(jwt == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        List<PrivateChat> chats = chatService.findAllByOwnerID(jwtUtil.getIDFromToken(jwt));

        chats.forEach(chat -> {
            User receiver = userService.findUserByID(chat.getReceiverID());
            chat.setReceiverFirstname(receiver.getFirstname());
            chat.setReceiverLastname(receiver.getLastname());
        });

        return ResponseEntity.ok(chats);
    }


    @GetMapping("/find-chat/{chatID}/{ownerID}")
    public ResponseEntity<PrivateChat> findChat(@RequestHeader Map<String, String> headers, @PathVariable long chatID, @PathVariable long ownerID)
    {
        DecodedJWT jwt = jwtUtil.checkJWTFromHeader(headers);

        if(jwt == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        PrivateChat chat = chatService.findUserChat(chatID, ownerID);

        if(chat != null)
            chat.setMessages(messageService.findAllByChatID(chat.getID()));
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        System.out.println(chat);

        return ResponseEntity.ok(chat);
    }

    @PostMapping("/create-chat")
    public ResponseEntity<PrivateChat> createChat(@RequestHeader Map<String, String> headers, @RequestBody ChatCreateDTO chatCreateDTO)
    {
        DecodedJWT jwt = jwtUtil.checkJWTFromHeader(headers);

        if(jwt == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        String receiverEmail = chatCreateDTO.getReceiverEmail();
        Optional<User> receiver = userService.findByMail(receiverEmail);

        if (receiver.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        long senderID = chatCreateDTO.getSenderID();
        long receiverID = receiver.get().getId();

        PrivateChat senderChat = chatService.findByOwnerIDAndReceiverID(senderID, receiverID);
        PrivateChat receiverChat = chatService.findByOwnerIDAndReceiverID(receiverID, senderID);

        if (senderChat != null && receiverChat != null)
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        if(senderChat == null)
        {
            senderChat = new PrivateChat();
            senderChat.setOwnerID(senderID);
            senderChat.setReceiverID(receiverID);
            senderChat = chatService.save(senderChat);
        }

        if(receiverChat == null && receiverID != senderID)
        {
            receiverChat = new PrivateChat();
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

        PrivateChatMessage senderMessage = messageService.convertToMessage(messageDTO);
        PrivateChatMessage receiverMessage = messageService.convertToMessage(messageDTO);

        PrivateChat senderChat = chatService.findByOwnerIDAndReceiverID(senderID, receiverID);
        PrivateChat receiverChat = chatService.findByOwnerIDAndReceiverID(receiverID, senderID);

        if(senderChat == null)
        {
            senderChat = new PrivateChat();
            senderChat.setOwnerID(senderID);
            senderChat.setReceiverID(receiverID);
        }

        if(receiverChat == null)
        {
            receiverChat = new PrivateChat();
            receiverChat.setOwnerID(receiverID);
            receiverChat.setReceiverID(senderID);
        }

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

        if(!receiverChat.isMuted())
            firebaseMessagingService.sendMessageNotification(senderMessage, receiverID, senderID);

        return ResponseEntity.ok(new ArrayList<>(List.of(senderDTO, receiverDTO)));
    }

    @PostMapping("/send-group-message")
    public ResponseEntity<MessageDTO> sendMessageToGroup(@RequestHeader Map<String, String> headers, @RequestBody MessageDTO messageDTO)
    {
        DecodedJWT jwt = jwtUtil.checkJWTFromHeader(headers);

        if (jwt == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        GroupChat groupChat = groupService.findGroupByID(messageDTO.getreceiverid());

        if (groupChat == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        GroupChatMessage senderMessage = groupChatMessageService.convertToMessage(messageDTO);

        senderMessage.setChatID(groupChat.getId());
        senderMessage = groupChatMessageService.saveMessage(senderMessage);

        groupChat.setUpdatedAt(OffsetDateTime.now());
        groupService.save(groupChat);

        MessageDTO senderDTO = groupChatMessageService.convertToMessageDTO(senderMessage);

        return ResponseEntity.ok(senderDTO);
    }

    @PostMapping("/action")
    public ResponseEntity<PrivateChatActionDTO> action(@RequestHeader Map<String, String> headers, @RequestBody PrivateChatActionDTO privateChatActionDTO)
    {
        DecodedJWT jwt = jwtUtil.checkJWTFromHeader(headers);

        if(jwt == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        PrivateChat secondUserChat;

        long receiverChatID;
        long chatID = privateChatActionDTO.getOwnerChatID();
        long ownerID = privateChatActionDTO.getOwnerUserID();
        long receiverID = privateChatActionDTO.getReceiverUserID();


        switch (privateChatActionDTO.getAction())
        {
            case CLEAR_CHAT_HISTORY -> {

                messageService.clearChatHistory(chatID);

                if (!privateChatActionDTO.getActionForBoth() || (secondUserChat = chatService.findByOwnerIDAndReceiverID(receiverID, ownerID)) == null)
                    return ResponseEntity.ok(privateChatActionDTO);

                receiverChatID = secondUserChat.getID();
                messageService.clearChatHistory(receiverChatID);
                privateChatActionDTO.setReceiverChatID(receiverChatID);

            }
            case DELETE_CHAT -> {

                messageService.clearChatHistory(chatID);
                chatService.deleteChat(chatID);

                if (!privateChatActionDTO.getActionForBoth() || (secondUserChat = chatService.findByOwnerIDAndReceiverID(receiverID, ownerID)) == null)
                    return ResponseEntity.ok(privateChatActionDTO);

                receiverChatID = secondUserChat.getID();

                messageService.clearChatHistory(receiverChatID);
                chatService.deleteChat(receiverChatID);

                privateChatActionDTO.setReceiverChatID(receiverChatID);
            }
            case MUTE_CHAT -> {

                long muteTime = privateChatActionDTO.getMuteTime();

                if(muteTime > 0)
                   muteTime += System.currentTimeMillis() / 1000L + privateChatActionDTO.getMuteTime();

                chatService.updateChatMuteTime(chatID, muteTime);
                privateChatActionDTO.setMuteTime(muteTime);
            }
            case BLOCK_CHAT -> {
                if ((secondUserChat = chatService.findByOwnerIDAndReceiverID(receiverID, ownerID)) == null)
                    return ResponseEntity.ok(privateChatActionDTO);
                secondUserChat.setBlocked(true);
                chatService.save(secondUserChat);
                PrivateChat userChat = chatService.findByOwnerIDAndReceiverID(ownerID, receiverID);
                userChat.setBlocking(true);
                chatService.save(userChat);
                privateChatActionDTO.setActionForBoth(true);
                privateChatActionDTO.setReceiverChatID(secondUserChat.getID());
            }
            case UNBLOCK_CHAT -> {
                if ((secondUserChat = chatService.findByOwnerIDAndReceiverID(receiverID, ownerID)) == null)
                    return ResponseEntity.ok(privateChatActionDTO);
                secondUserChat.setBlocked(false);
                chatService.save(secondUserChat);
                PrivateChat userChat = chatService.findByOwnerIDAndReceiverID(ownerID, receiverID);
                userChat.setBlocking(false);
                chatService.save(userChat);
                privateChatActionDTO.setActionForBoth(true);
                privateChatActionDTO.setReceiverChatID(secondUserChat.getID());
            }
        }

        return ResponseEntity.ok(privateChatActionDTO);
    }

    @PostMapping("/create/group")
    public ResponseEntity<GroupChat> createGroup(@RequestHeader Map<String, String> headers, @RequestBody CreateGroupDTO createGroupDTO)
    {
        DecodedJWT jwt = jwtUtil.checkJWTFromHeader(headers);

        if(jwt == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        long groupID;
        GroupChat group = new GroupChat();

        group.setGroupName(createGroupDTO.getgroupname());

        group = groupService.save(group);
        groupID = group.getId();

        for (long id : createGroupDTO.getMembers())
        {
            System.out.println(id + " " + groupID);
            groupMemberService.save(new GroupMember(id, groupID));
        }
        return ResponseEntity.ok(group);
    }

    @GetMapping("/group/get/{id}")
    public ResponseEntity<GroupChat> getGroup(@RequestHeader Map<String, String> headers, @PathVariable long id)
    {
        DecodedJWT jwt = jwtUtil.checkJWTFromHeader(headers);

        if(jwt == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        GroupChat group = groupService.findGroupByID(id);

        if(group == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        group.setGroupMembers(groupMemberService.findAllGroupMembers(id));

        return ResponseEntity.ok(group);
    }
}
