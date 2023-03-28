package com.server.talkster.models;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "tokens")
public class FCMToken {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @Column(name = "owner_id")
    private long ownerID;

    @Column(name = "token")
    private String token;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    public FCMToken() { updatedAt = OffsetDateTime.now(); }

    public  FCMToken(long ownerID, String token)
    {
        this.ownerID = ownerID;
        this.token = token;
        this.updatedAt = OffsetDateTime.now();
    }

    public String getToken() {return token;}
    public void setToken(String token) {this.token = token;}
    public void setOwnerID(long ownerID) {this.ownerID = ownerID;}
    public void setUpdatedAt() {this.updatedAt = OffsetDateTime.now();}
}