package com.motorny.config;

public class CustomFeignException extends Exception {

    public CustomFeignException() {
        super();
    }

    public CustomFeignException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "CustomFeignException: " + getMessage();
    }
}
