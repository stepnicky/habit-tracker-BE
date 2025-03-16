package com.habittracker.backend.feature.user.model;

import java.util.UUID;

public record UserDTO(UUID id,
                      String username,
                      String firstName,
                      String lastName,
                      String email) {
}
