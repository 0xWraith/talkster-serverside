package com.server.talkster.repositories;

import com.server.talkster.models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long>
{

    @Query("SELECT new Chat(c.ID, c.ownerID, c.receiverID, c.muteTime, u.lastname, u.firstname) FROM Chat c JOIN User u ON c.receiverID = u.id WHERE c.ownerID = :ownerID ORDER BY c.updatedAt DESC")
    List<Chat> findAllByOwnerID(Long ownerID);

    @Query("SELECT new Chat(c.ID, c.ownerID, c.receiverID, c.muteTime, u.lastname, u.firstname) FROM Chat c JOIN User u ON c.receiverID = u.id WHERE c.ID = :chatID AND c.ownerID = :ownerID ORDER BY c.updatedAt DESC")
    Chat findByIDAndOwnerID(long chatID, long ownerID);

    Chat findByOwnerIDAndReceiverID(Long ownerID, Long receiverID);

    @Modifying
    @Transactional
    @Query("UPDATE Chat AS c SET c.muteTime = :muteTime WHERE c.ID = :chatID")
    void updateChatMuteTime(long chatID, long muteTime);

    @Modifying
    @Transactional
    @Query("DELETE FROM Chat AS c WHERE c.ID = :chatID")
    void deleteByChatID(long chatID);
}
