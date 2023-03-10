package com.server.talkster.services;

import com.server.talkster.models.AuthKey;
import com.server.talkster.repositories.AuthKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthKeyService
{
    private final AuthKeyRepository authKeyRepository;

    @Autowired
    public AuthKeyService(AuthKeyRepository authKeyRepository) { this.authKeyRepository = authKeyRepository; }

    public void createAuthKey(Long ownerID, String mail, String key)
    {
        AuthKey authKey = new AuthKey();

        authKey.setCode(key);
        authKey.setMail(mail);
        authKey.setOwnerID(ownerID);

        authKeyRepository.save(authKey);
    }
}
