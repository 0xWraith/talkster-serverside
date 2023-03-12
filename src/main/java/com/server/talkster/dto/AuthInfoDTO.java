package com.server.talkster.dto;

public class AuthInfoDTO
{
    private long id;
    private String jwtToken;

    public long getID() { return id; }
    public String getJWTToken() { return jwtToken; }

    public void setID(long id) { this.id = id; }
    public void setJWTToken(String jwtToken) { this.jwtToken = jwtToken; }
}
