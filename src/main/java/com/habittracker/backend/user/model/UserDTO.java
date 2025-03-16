package com.habittracker.backend.user.model;

import java.util.UUID;

public record UserDTO(UUID id,
                      String username,
                      String firstName,
                      String lastName,
                      String email) {
}
