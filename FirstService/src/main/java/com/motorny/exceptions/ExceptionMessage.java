package com.motorny.exceptions;

public record ExceptionMessage(String timestamp, int status, String error, String message, String path) {

}
