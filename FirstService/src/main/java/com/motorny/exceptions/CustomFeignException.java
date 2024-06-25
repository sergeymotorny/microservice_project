package com.motorny.exceptions;


public class CustomFeignException extends RuntimeException {

    public CustomFeignException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "CustomFeignException: " + getMessage();
    }
}
