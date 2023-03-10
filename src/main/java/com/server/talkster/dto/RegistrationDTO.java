package com.server.talkster.dto;

public class RegistrationDTO extends AuthenticationDTO
{

    private String firstName;
    private String lastName;

    public String getLastName() { return lastName; }
    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
}
