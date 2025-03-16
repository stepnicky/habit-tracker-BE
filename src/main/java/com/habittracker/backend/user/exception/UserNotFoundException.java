package com.habittracker.backend.user.exception;

import com.habittracker.backend.exception.EntityNotFoundException;

import java.util.UUID;

public class UserNotFoundException extends EntityNotFoundException {

    private static final String ENTITY_TYPE = "user";

    public UserNotFoundException(UUID entityId) {
        super(entityId, ENTITY_TYPE);
    }
}

