package com.server.talkster.dto;

public class AuthInfoDTO
{
    private String jwtToken;

    public String getJWTToken() { return jwtToken; }
    public void setJWTToken(String jwtToken) { this.jwtToken = jwtToken; }
}
