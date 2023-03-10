package com.server.talkster.utility.exceptions.User;

public class UserNotFoundException extends RuntimeException
{
    public UserNotFoundException(String exceptionMessage) { super(exceptionMessage); }
}
