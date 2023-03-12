package com.server.talkster.services;

import com.server.talkster.dto.RegistrationDTO;
import com.server.talkster.models.AuthKey;
import com.server.talkster.models.User;
import com.server.talkster.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService
{
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper)
    {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    public Optional<User> findByMail(String mail) { return this.userRepository.findByMail(mail); }
    public User convertToUser(RegistrationDTO registrationDTO) { return modelMapper.map(registrationDTO, User.class); }

    public Long createUser(User user) { return userRepository.save(user).getId(); }
}
