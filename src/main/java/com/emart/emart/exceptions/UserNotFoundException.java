package com.emart.emart.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String msg) {
    super(msg);
    }
}
