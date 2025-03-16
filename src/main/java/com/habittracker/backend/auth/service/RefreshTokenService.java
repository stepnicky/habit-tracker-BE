package com.habittracker.backend.auth.service;

import com.habittracker.backend.feature.user.persistence.User;
import java.util.UUID;

public interface RefreshTokenService {

  UUID generateToken(User userId);

  Boolean isTokenValid(UUID token);

  User getIssuedFor(UUID token);

  void removeToken(UUID token);

  void removeExpiredTokens();
}
