package com.habittracker.backend.auth.service.impl;

import com.habittracker.backend.auth.exception.InvalidOrMissingRefreshTokenException;
import com.habittracker.backend.auth.model.RefreshToken;
import com.habittracker.backend.auth.persistence.RefreshTokenRepository;
import com.habittracker.backend.auth.service.RefreshTokenService;
import com.habittracker.backend.feature.user.persistence.User;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

  @Value("${refresh-token.expiration-time.ms}")
  private Long REFRESH_TOKEN_EXPIRATION_TIME;
  private final RefreshTokenRepository refreshTokenRepository;

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Override
  @Transactional
  public UUID generateToken(User user) {
    logger.debug("Generating refresh token");
    RefreshToken token = refreshTokenRepository.save(new RefreshToken(user,
        new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME)));
    return token.getId();
  }

  @Override
  public Boolean isTokenValid(UUID token) {
    logger.debug("Checking if refresh token is not expired");
    Date expirationTime = getToken(token).getExpirationTime();
    return new Date().before(expirationTime);
  }

  @Override
  public User getIssuedFor(UUID token) {
    return getToken(token).getIssuedFor();
  }

  @Override
  @Transactional
  public void removeToken(UUID token) {
    if (refreshTokenRepository.findById(token).isEmpty()) {
      logger.debug("Failed to delete refresh token. Token is not in database");
      throw new InvalidOrMissingRefreshTokenException(
          "Log out failed due to invalid or missing refresh token.");
    }
    refreshTokenRepository.deleteById(token);
  }

  @Transactional
  public void removeExpiredTokens() {
    List<RefreshToken> expiredTokens = refreshTokenRepository.findByExpirationTimeLessThan(
        new Date(System.currentTimeMillis()));
    expiredTokens.forEach(token -> removeToken(token.getId()));
  }

  private RefreshToken getToken(UUID token) {
    try {
      logger.debug("Fetching token from database");
      return refreshTokenRepository.getReferenceById(token);
    } catch (Exception ex) {
      logger.debug("Token is not in database");
      throw new InvalidOrMissingRefreshTokenException(
          "Invalid or missing refresh token.");
    }
  }
}
