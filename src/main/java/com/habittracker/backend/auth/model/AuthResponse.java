package com.habittracker.backend.auth.model;

import java.util.UUID;

public record AuthResponse(String accessToken, UUID refreshToken, String language) {

}

