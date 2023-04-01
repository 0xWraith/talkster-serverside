package com.server.talkster.dto;


import com.server.talkster.models.User;

public class VerifiedUserDTO
{
    private User user;
    private UserJWT userJWT;

    public VerifiedUserDTO() {}

    public User getUser() { return user; }
    public UserJWT getUserJWT() { return userJWT; }

    public void setUser(User user) { this.user = user; }
    public void setUserJWT(UserJWT userJWT) { this.userJWT = userJWT; }

    @Override
    public String toString() {
        return "VerifiedUserDTO{" +
                "user=" + user +
                ", userJWT=" + userJWT +
                '}';
    }
}
