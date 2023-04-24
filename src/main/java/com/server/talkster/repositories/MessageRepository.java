package com.server.talkster.repositories;

import com.server.talkster.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>
{
    Message findTopByChatID(Long chatID);
    List<Message> findAllByChatIDOrderById(Long chatID);

    @Modifying
    @Transactional
    @Query("DELETE FROM Message AS m WHERE m.chatID = :chatID")
    void deleteAllByChatID(Long chatID);


//    void removeAllByChatIDAndSenderIDAndReceiverID(Long chatID, Long senderID, Long receiverID);
}
