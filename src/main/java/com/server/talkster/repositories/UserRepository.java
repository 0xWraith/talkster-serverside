package com.server.talkster.repositories;

import com.server.talkster.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    User findById(long id);
    Optional<User> findByMail(String mail);
}
