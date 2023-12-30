package com.emart.emart.exceptions;

public class CartIsEmptyException extends RuntimeException {
    public CartIsEmptyException(String msg) {
        super(msg);
    }
}
