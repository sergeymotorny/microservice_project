package com.motorny.exception;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return "Forbidden exception: " + getMessage();
    }
}
