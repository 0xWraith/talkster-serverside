package com.server.talkster.repositories;

import com.server.talkster.models.Contact;
import com.server.talkster.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long>
{
    List<Contact> findAllByUserID(long userID);

    //    Join account table and get all user data
    @Query("SELECT new User(u.id, u.firstname, u.lastname, u.mail, u.username, u.biography, u.imageID) FROM Contact AS c JOIN User AS u ON c.contactID = u.id WHERE c.userID = :userID")
    List<User> getContactUserData(long userID);
}