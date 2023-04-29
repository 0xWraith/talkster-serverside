package com.server.talkster.services.chat;

import com.server.talkster.models.PrivateChat;
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

    public PrivateChat save(PrivateChat chat) { return chatRepository.save(chat); }
    public void deleteChat(long chatID) { chatRepository.deleteByChatID(chatID); }
    public List<PrivateChat> findAllByOwnerID(Long ownerID) { return chatRepository.findAllByOwnerID(ownerID); }
    public PrivateChat findUserChat(long chatID, long ownerID) { return chatRepository.findByIDAndOwnerID(chatID, ownerID); }
    public void updateChatMuteTime(long chatID, long muteTime) { chatRepository.updateChatMuteTime(chatID, muteTime); }
    public PrivateChat findByOwnerIDAndReceiverID(Long ownerID, Long receiverID) { return chatRepository.findByOwnerIDAndReceiverID(ownerID, receiverID); }


}
