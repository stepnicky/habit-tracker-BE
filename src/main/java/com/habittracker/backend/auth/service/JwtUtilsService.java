package com.habittracker.backend.auth.service;

import com.habittracker.backend.feature.user.persistence.User;

public interface JwtUtilsService {

    String extractUsername(String token);

    String generateToken(
            User user
    );

    boolean isTokenValid(String token);
}
