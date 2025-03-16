package com.habittracker.backend.feature.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidPrincipalException extends RuntimeException {
    public InvalidPrincipalException(String message) {
        super(message);
    }
}
