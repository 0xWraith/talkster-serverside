package com.server.talkster.dto;

public class UserJWT
{
    private long id;
    private String accessToken;
    private String refreshToken;

    public long getID() { return id; }
    public String getAccessToken() { return accessToken; }
    public String getRefreshToken() { return refreshToken; }

    public void setID(long id) { this.id = id; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }

    @Override
    public String toString() {
        return "UserJWT{" +
                "id=" + id +
                ", accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
