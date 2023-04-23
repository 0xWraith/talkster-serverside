package com.server.talkster.services;

import com.server.talkster.models.Chat;
import com.server.talkster.repositories.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService
{
    private final ChatRepository chatRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository) { this.chatRepository = chatRepository; }

    public List<Chat> findAllByOwnerID(Long ownerID) { return chatRepository.findAllByOwnerID(ownerID); }
    public Chat findUserChat(long chatID, long ownerID) { return chatRepository.findByIDAndOwnerID(chatID, ownerID); }
    public Chat findByID(long chatID) {return chatRepository.findByID(chatID);}
    public Chat findByOwnerIDAndReceiverID(Long ownerID, Long receiverID) { return chatRepository.findByOwnerIDAndReceiverID(ownerID, receiverID); }
    public Chat save(Chat chat) { return chatRepository.save(chat); }
}
