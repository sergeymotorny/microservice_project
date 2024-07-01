package com.motorny.exceptions;

import java.time.LocalDateTime;

public record ErrorResponse(LocalDateTime dateTime, int statusCode, String message, String path) { }