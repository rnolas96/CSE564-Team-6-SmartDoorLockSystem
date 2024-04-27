package com.smartdoor.exceptions;

public class AddUserException extends RuntimeException{
    private int statusCode;

    public AddUserException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
