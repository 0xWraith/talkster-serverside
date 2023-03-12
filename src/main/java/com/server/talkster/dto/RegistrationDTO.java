package com.server.talkster.dto;

public class RegistrationDTO extends AuthenticationDTO
{

    private String firstname;
    private String lastname;
    private String mail;

    public String getMail() { return mail; }
    public String getLastname() { return lastname; }
    public String getFirstname() { return firstname; }

    public void setMail(String mail) { this.mail = mail; }
    public void setLastname(String lastname) { this.lastname = lastname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }

    @Override
    public String toString() {
        return "RegistrationDTO{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }
}
