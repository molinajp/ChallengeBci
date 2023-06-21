package com.challenge.bci.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserEmailNotUniqueException extends RuntimeException {

    private static final long serialVersionUID = -875917576558141555L;

    public UserEmailNotUniqueException(final String message) {
        super(message);
    }
}
