package com.server.talkster.models;

import jakarta.persistence.*;

@Entity(name = "Contact")
@Table(name = "user_contacts")
public class Contact
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "user_id")
    private long userID;

    @Column(name = "contact_id")
    private long contactID;

    public Contact() { }

    public Contact(long userID, long contactID)
    {
        this.userID = userID;
        this.contactID = contactID;
    }

    public long getId() { return id; }
    public long getUserID() { return userID; }
    public long getContactID() { return contactID; }

    public void setUserID(long userID) { this.userID = userID; }
    public void setContactID(long contactID) { this.contactID = contactID; }
}