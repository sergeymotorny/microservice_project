package com.motorny.exception;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return "Unauthorized exception: " + getMessage();
    }
}
