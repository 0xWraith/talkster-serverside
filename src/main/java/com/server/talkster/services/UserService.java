package com.server.talkster.services;

import com.server.talkster.models.AuthKey;
import com.server.talkster.models.User;
import com.server.talkster.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService
{
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) { this.userRepository = userRepository; }

    public Optional<User> findByMail(String mail) { return this.userRepository.findByMail(mail); }

}
