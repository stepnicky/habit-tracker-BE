package com.habittracker.backend.exception;

import org.springframework.http.HttpStatus;

public abstract class InvalidDataException extends ApplicationException {

    public InvalidDataException(String message) {
        super(message);
    }

    @Override
    HttpStatus getResponseStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
