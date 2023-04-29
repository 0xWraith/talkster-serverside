package com.server.talkster.services;

import com.server.talkster.models.Contact;
import com.server.talkster.models.User;
import com.server.talkster.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService
{
    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) { this.contactRepository = contactRepository; }

    public List<Contact> findAllByUserID(long userID) { return contactRepository.findAllByUserID(userID); }
    public List<User> getContactUserData(long userID) { return contactRepository.getContactUserData(userID); }

    public Contact createContact(Contact contact) { return contactRepository.save(contact); }
}
