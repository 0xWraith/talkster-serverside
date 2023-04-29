package com.server.talkster.repositories;

import com.server.talkster.models.GroupChatMessage;
import com.server.talkster.models.PrivateChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupChatMessageRepository extends JpaRepository<GroupChatMessage, Long>
{
    List<GroupChatMessage> findAllByChatIDOrderById(Long chatID);
}
