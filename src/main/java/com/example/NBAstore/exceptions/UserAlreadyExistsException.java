package com.example.NBAstore.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String message) {
        super("Email address is already taken " + message);
    }
}
