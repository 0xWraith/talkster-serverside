package com.server.talkster.dto;

public class AuthenticationDTO
{
//    protected Long id;
    protected String code;
    protected String mail;

//    public Long getId() { return id; }
    public String getCode() { return code; }
    public String getMail() { return mail; }

//    public void setId(Long id) { this.id = id; }
    public void setCode(String code) { this.code = code; }
    public void setMail(String mail) { this.mail = mail; }

    @Override
    public String toString() {
        return "AuthenticationDTO{" +
                "code='" + code + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }
}
