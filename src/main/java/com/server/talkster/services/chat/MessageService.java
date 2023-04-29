package com.server.talkster.services.chat;

import com.server.talkster.dto.MessageDTO;
import com.server.talkster.models.PrivateChatMessage;
import com.server.talkster.repositories.MessageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService
{
    private final ModelMapper modelMapper;
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(ModelMapper modelMapper, MessageRepository messageRepository)
    {
        this.modelMapper = modelMapper;
        this.messageRepository = messageRepository;
    }

    public PrivateChatMessage saveMessage(PrivateChatMessage message) { return messageRepository.save(message); }
    public void clearChatHistory(Long chatID) { messageRepository.deleteAllByChatID(chatID); }
    public List<PrivateChatMessage> findAllByChatID(Long chatID) { return messageRepository.findAllByChatIDOrderById(chatID); }
    public PrivateChatMessage findLastMessageByChatID(Long chatID) { return messageRepository.findTopByChatID(chatID); }


    public PrivateChatMessage convertToMessage(MessageDTO messageDTO) { return modelMapper.map(messageDTO, PrivateChatMessage.class); }
    public MessageDTO convertToMessageDTO(PrivateChatMessage message) { return modelMapper.map(message, MessageDTO.class); }

}
