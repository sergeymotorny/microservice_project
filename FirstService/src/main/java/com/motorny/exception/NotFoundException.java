package com.motorny.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {  }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return "Not found exception: " + getMessage();
    }
}
