package com.server.talkster.dto;

public class AuthenticationDTO
{
    protected String code;
    protected String mail;

    public String getCode() { return code; }
    public String getMail() { return mail; }

    public void setCode(String code) { this.code = code; }
    public void setMail(String mail) { this.mail = mail; }
}
