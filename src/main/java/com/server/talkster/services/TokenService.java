package com.server.talkster.services;

import com.server.talkster.models.FCMToken;
import com.server.talkster.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TokenService
{
    private final TokenRepository tokenRepository;

    @Autowired
    public TokenService(TokenRepository tokenRepository) { this.tokenRepository = tokenRepository; }

    public FCMToken saveToken(FCMToken fcmToken) { return tokenRepository.save(fcmToken); }
    public void removeAllByToken(String token) { tokenRepository.removeAllByToken(token);}
    public FCMToken findFirstByToken(String token) { return tokenRepository.findFirstByToken(token); }
    public List<FCMToken> findAllByOwnerID(Long ownerID) { return tokenRepository.findAllByOwnerID(ownerID); }
}
