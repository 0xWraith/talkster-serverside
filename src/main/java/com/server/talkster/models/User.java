package com.server.talkster.models;

import jakarta.persistence.*;
import org.checkerframework.checker.units.qual.C;

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

    @Column(name = "username")
    private String username;

    @Column(name = "biography")
    private String biography;

    @Column(name = "image_id")
    private Long imageID;

    public User() {}

    public Long getId() { return id; }
    public String getMail() { return mail; }
    public Long getImageID() {return imageID;}
    public String getUsername() { return username; }
    public String getLastname() { return lastname; }
    public String getBiography() { return biography; }
    public String getFirstname() { return firstname; }

    public void setId(Long id) { this.id = id; }
    public void setMail(String mail) { this.mail = mail; }
    public void setImageID(Long imageID) {this.imageID = imageID;}
    public void setUsername(String username) { this.username = username; }
    public void setLastname(String lastname) { this.lastname = lastname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }
    public void setBiography(String biography) { this.biography = biography; }

    public String getFullName() { return firstname + " " + lastname; }
}
