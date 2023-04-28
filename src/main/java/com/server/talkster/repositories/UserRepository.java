package com.server.talkster.repositories;

import com.server.talkster.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

//@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    User findById(long id);
    Optional<User> findByMail(String mail);

    @Query("SELECT u.id FROM User AS u WHERE u.username = :username")
    Optional<Long> findIdByUsername(String username);
}
