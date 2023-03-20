package com.server.talkster.repositories;

import com.server.talkster.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>
{
    Message findTopByChatID(Long chatID);
    List<Message> findAllByChatID(Long chatID);
}
