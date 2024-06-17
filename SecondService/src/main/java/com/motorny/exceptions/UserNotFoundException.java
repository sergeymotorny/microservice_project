package com.motorny.exceptions;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserNotFoundException extends DataAccessException {

    public UserNotFoundException(String msg) {
        super(msg);
    }

    public UserNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public synchronized Throwable getCause() {
        return super.getCause();
    }
}
