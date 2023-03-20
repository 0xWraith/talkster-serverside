package com.server.talkster.models;

import jakarta.persistence.*;

@Entity(name = "User")
@Table(name = "\"accounts\"", schema = "public")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "mail")
    private String mail;

    public User() {}

    public User(String firstname, String lastname, String mail)
    {
        this.firstname = firstname;
        this.lastname = lastname;
        this.mail = mail;
    }

    public Long getId() { return id; }
    public String getMail() { return mail; }
    public String getLastname() { return lastname; }
    public String getFirstname() { return firstname; }

    public void setId(Long id) { this.id = id; }
    public void setMail(String mail) { this.mail = mail; }
    public void setLastname(String lastname) { this.lastname = lastname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }
}
