package com.habittracker.backend.feature.group.exception;

import com.habittracker.backend.exception.EntityNotFoundException;

import java.util.UUID;

public class GroupNotFoundException extends EntityNotFoundException {

    private static final String ENTITY_TYPE = "group";

    public GroupNotFoundException(UUID entityId) {
        super(entityId, ENTITY_TYPE);
    }
}
