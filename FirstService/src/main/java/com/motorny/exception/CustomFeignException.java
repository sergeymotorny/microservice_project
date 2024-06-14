package com.motorny.exception;

public class CustomFeignException extends RuntimeException {

    public CustomFeignException(String message) {
        super(message);
    }

    public CustomFeignException(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return "CustomFeignException: " + getMessage();
    }
}
