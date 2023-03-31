package com.server.talkster.repositories;

import com.server.talkster.models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long>
{

    @Query("SELECT new Chat(c.ID, c.ownerID, c.receiverID, u.lastname, u.firstname) FROM Chat c JOIN User u ON c.receiverID = u.id WHERE c.ownerID = :ownerID ORDER BY c.updatedAt DESC")
    List<Chat> findAllByOwnerID(Long ownerID);
    @Query("SELECT new Chat(c.ID, c.ownerID, c.receiverID, u.lastname, u.firstname) FROM Chat c JOIN User u ON c.receiverID = u.id WHERE c.ID = :chatID AND c.ownerID = :ownerID ORDER BY c.updatedAt DESC")
    Chat findByIDAndOwnerID(long chatID, long ownerID);
    Chat findByOwnerIDAndReceiverID(Long ownerID, Long receiverID);
}
