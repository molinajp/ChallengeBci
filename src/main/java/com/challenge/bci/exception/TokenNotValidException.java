package com.challenge.bci.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TokenNotValidException extends RuntimeException {

    private static final long serialVersionUID = -3609914931997702010L;

    public TokenNotValidException(final String message) {
        super(message);
    }

}
