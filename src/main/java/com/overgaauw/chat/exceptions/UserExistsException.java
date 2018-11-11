package com.overgaauw.chat.exceptions;

public class UserExistsException extends RuntimeException {

    public UserExistsException(String errorMessage) {
        super(errorMessage);
    }
}
