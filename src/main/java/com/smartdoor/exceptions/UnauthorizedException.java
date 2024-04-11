package com.smartdoor.exceptions;

public class UnauthorizedException extends RuntimeException {
    private int statusCode;

    public UnauthorizedException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
