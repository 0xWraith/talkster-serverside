package com.server.talkster.repositories;

import com.server.talkster.models.FCMToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenRepository extends JpaRepository<FCMToken, Long> {
    List<FCMToken> findAllByOwnerID(Long ownerID);
    FCMToken findFirstByToken(String token);
    void removeAllByToken(String token);
}
