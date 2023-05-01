package com.server.talkster.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "User")
@Table(name = "\"accounts\"", schema = "public")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

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

    @Column(name = "map_tracker")
    private boolean mapTracker;

    @Transient
    private List<User> contacts;

    public User() { contacts = new ArrayList<>(); }

    public User(long id, String firstname, String lastname, String mail, String username, String biography, Long imageID)
    {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.mail = mail;
        this.username = username;
        this.biography = biography;
        this.imageID = imageID;
        contacts = null;
        mapTracker = false;
    }

    public User(long id, String firstname, String lastname, String username, Long imageID)
    {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.imageID = imageID;
        contacts = null;
        mapTracker = false;
    }

    public Long getId() { return id; }
    public String getMail() { return mail; }
    public Long getImageID() {return imageID;}
    public String getUsername() { return username; }
    public String getLastname() { return lastname; }
    public String getFirstname() { return firstname; }
    public String getBiography() { return biography; }
    public List<User> getContacts() { return contacts; }
    public boolean getMapTracker() { return mapTracker; }
    public String getFullName() { return firstname + " " + lastname; }

    public void setId(Long id) { this.id = id; }
    public void setMail(String mail) { this.mail = mail; }
    public void addContact(User user) { contacts.add(user); }
    public void setImageID(Long imageID) {this.imageID = imageID;}
    public void setLastname(String lastname) { this.lastname = lastname; }

    public void setUsername(String username) { this.username = username; }
    public void setBiography(String biography) { this.biography = biography; }
    public void setFirstname(String firstname) { this.firstname = firstname; }
    public void setContacts(List<User> contacts) { this.contacts = contacts; }
    public void setMapTracker(boolean mapTracker) { this.mapTracker = mapTracker; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", mail='" + mail + '\'' +
                ", username='" + username + '\'' +
                ", biography='" + biography + '\'' +
                ", imageID=" + imageID +
                '}';
    }
}
