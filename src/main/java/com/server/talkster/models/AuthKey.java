package com.server.talkster.models;

import jakarta.persistence.*;

@Entity(name = "AuthKey")
@Table(name = "\"auth_keys\"", schema = "public")

public class AuthKey
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "owner_id")
    private Long owner_id;

    @Column(name = "mail")
    private String mail;

    @Column(name = "code")
    private String code;

    public Long getId() { return id; }
    public String getMail() { return mail; }
    public String getCode() { return code; }
    public Long getOwnerID() { return owner_id; }

    public void setId(Long id) { this.id = id; }
    public void setMail(String mail) { this.mail = mail; }
    public void setCode(String code) { this.code = code; }
    public void setOwnerID(Long ownerID) { this.owner_id = ownerID; }
}
