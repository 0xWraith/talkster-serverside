package com.server.talkster.services.chat;

import com.server.talkster.dto.MessageDTO;
import com.server.talkster.models.GroupChatMessage;
import com.server.talkster.models.PrivateChatMessage;
import com.server.talkster.repositories.GroupChatMessageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class GroupChatMessageService
{
    private final ModelMapper modelMapper;
    private final GroupChatMessageRepository groupChatMessageRepository;

    @Autowired
    public GroupChatMessageService(ModelMapper modelMapper, GroupChatMessageRepository groupChatMessageRepository)
    {
        this.modelMapper = modelMapper;
        this.groupChatMessageRepository = groupChatMessageRepository;
    }

    public List<GroupChatMessage> findAllByChatID(Long chatID) { return groupChatMessageRepository.findAllByChatIDOrderById(chatID); }

    public GroupChatMessage saveMessage(GroupChatMessage message) { return groupChatMessageRepository.save(message); }

    public MessageDTO convertToMessageDTO(GroupChatMessage message) { return modelMapper.map(message, MessageDTO.class); }
    public GroupChatMessage convertToMessage(MessageDTO messageDTO) { return modelMapper.map(messageDTO, GroupChatMessage.class); }


}
