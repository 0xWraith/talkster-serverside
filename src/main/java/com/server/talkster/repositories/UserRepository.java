package com.server.talkster.repositories;

import com.server.talkster.models.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    Optional<User> findByMail(String mail);
}
