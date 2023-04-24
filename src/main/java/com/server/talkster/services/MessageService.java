package com.server.talkster.services;

import com.server.talkster.dto.MessageDTO;
import com.server.talkster.models.Message;
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

    public Message saveMessage(Message message) { return messageRepository.save(message); }
    public void clearChatHistory(Long chatID) { messageRepository.deleteAllByChatID(chatID); }
    public List<Message> findAllByChatID(Long chatID) { return messageRepository.findAllByChatIDOrderById(chatID); }
    public Message findLastMessageByChatID(Long chatID) { return messageRepository.findTopByChatID(chatID); }


    public Message convertToMessage(MessageDTO messageDTO) { return modelMapper.map(messageDTO, Message.class); }
    public MessageDTO convertToMessageDTO(Message message) { return modelMapper.map(message, MessageDTO.class); }

}
