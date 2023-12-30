package com.emart.emart.exceptions;

public class EmailAlreadyExistException extends RuntimeException {
    public EmailAlreadyExistException(String msg) {
    super(msg);
    }
}
