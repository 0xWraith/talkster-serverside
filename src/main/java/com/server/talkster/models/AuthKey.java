package com.server.talkster.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity(name = "AuthKey")
@Table(name = "\"auth_keys\"", schema = "public")

public class AuthKey
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ownerid")
    private Long ownerid;

    @Column(name = "mail")
    private String mail;

    @Column(name = "code")
    private String code;

    public Long getId() { return id; }
    public String getMail() { return mail; }
    public String getCode() { return code; }
    public Long getownerid() { return ownerid; }

    public void setId(Long id) { this.id = id; }
    public void setMail(String mail) { this.mail = mail; }
    public void setCode(String code) { this.code = code; }
    public void setownerid(Long ownerid) { this.ownerid = ownerid; }

    @Override
    public String toString() {
        return "AuthKey{" +
                "id=" + id +
                ", ownerid=" + ownerid +
                ", mail='" + mail + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
