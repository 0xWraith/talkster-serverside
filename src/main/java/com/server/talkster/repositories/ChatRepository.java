package com.server.talkster.repositories;

import com.server.talkster.models.PrivateChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ChatRepository extends JpaRepository<PrivateChat, Long>
{

    @Query("SELECT new PrivateChat(c.ID, c.ownerID, c.receiverID, c.muteTime, c.isBlocked, c.isBlocking, c.type, u.lastname, u.firstname) FROM PrivateChat c JOIN User u ON c.receiverID = u.id WHERE c.ownerID = :ownerID ORDER BY c.updatedAt DESC")
    List<PrivateChat> findAllByOwnerID(Long ownerID);

    @Query("SELECT new PrivateChat(c.ID, c.ownerID, c.receiverID, c.muteTime, c.isBlocked, c.isBlocking, c.type, u.lastname, u.firstname) FROM PrivateChat c JOIN User u ON c.receiverID = u.id WHERE c.ID = :chatID AND c.ownerID = :ownerID ORDER BY c.updatedAt DESC")
    PrivateChat findByIDAndOwnerID(long chatID, long ownerID);

    PrivateChat findByOwnerIDAndReceiverID(Long ownerID, Long receiverID);

    @Modifying
    @Transactional
    @Query("UPDATE PrivateChat AS c SET c.muteTime = :muteTime WHERE c.ID = :chatID")
    void updateChatMuteTime(long chatID, long muteTime);

    @Modifying
    @Transactional
    @Query("DELETE FROM PrivateChat AS c WHERE c.ID = :chatID")
    void deleteByChatID(long chatID);
}
