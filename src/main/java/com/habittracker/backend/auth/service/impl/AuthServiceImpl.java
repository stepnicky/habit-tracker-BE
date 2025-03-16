package com.habittracker.backend.auth.service.impl;

import com.habittracker.backend.feature.user.model.Language;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.habittracker.backend.auth.exception.InvalidOrMissingRefreshTokenException;
import com.habittracker.backend.auth.model.AuthRequest;
import com.habittracker.backend.auth.model.AuthResponse;
import com.habittracker.backend.auth.service.AuthService;
import com.habittracker.backend.auth.service.JwtUtilsService;
import com.habittracker.backend.auth.service.RefreshTokenService;
import com.habittracker.backend.feature.user.persistence.User;
import com.habittracker.backend.feature.user.service.UserService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final JwtUtilsService jwtService;
  private final AuthenticationManager authenticationManager;

  private final RefreshTokenService refreshTokenService;

  private final UserService userService;

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Override
  public AuthResponse authenticate(AuthRequest request) {
    logger.debug("Starting to validate credentials");
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.username(),
            request.password()
        )
    );
    String accessToken = jwtService.generateToken((User) authentication.getPrincipal());
    User user = userService.getUserByName(request.username());
    UUID refreshToken = refreshTokenService.generateToken(user);
    Language language = user.getLanguage();
    return new AuthResponse(accessToken, refreshToken, language.getCode());
  }

  @Override
  public AuthResponse refreshAccessToken(String token) {
    try {
      logger.debug("Converting token from string to UUID");
      UUID tokenValue = UUID.fromString(token);
      logger.debug("Starting to validate token");
      if (refreshTokenService.isTokenValid(tokenValue)) {
        User user = refreshTokenService.getIssuedFor(tokenValue);
        String accessToken = jwtService.generateToken(user);
        logger.debug("Token is valid");
        Language language = user.getLanguage();
        return new AuthResponse(accessToken, tokenValue, language.getCode());
      } else {
        throw new InvalidOrMissingRefreshTokenException(
            "Access token could not be refreshed due to invalid or missing refresh token.");
      }
    } catch (Exception e) {
      throw new InvalidOrMissingRefreshTokenException(
          "Access token could not be refreshed due to invalid or missing refresh token.");
    }
  }

  @Override
  public void logOut(String token) {
    try {
     /*
     Function first tries to convert token from request to UUID.
     Conversion will throw error if token is invalid,
     conversion error will be caught in catch below and
     log out will fail.
      */

      logger.debug("Converting token from string to UUID");
      UUID tokenValue = UUID.fromString(token);

      /*
      After conversion,
      check if user is 'owner' of the refresh token.
      'owner' = refresh token has 'issuedFor' property equals to userId.

      This prevents scenario when user gained access to token issued for other user
      and tries to log out the other user.

      Exception will be also thrown if token is not in database.

      There are no more validations - check for expiration date is not performed, because
       user can try to log out with still valid access token and expired refresh token.
       */
      if (!isAuthenticatedUserOwnerOfRefreshToken(tokenValue)) {
        logger.debug("Token wasn't issued for the user requesting log out");
        throw new InvalidOrMissingRefreshTokenException(
            "Log out failed due to invalid or missing refresh token");
      }
      refreshTokenService.removeToken(tokenValue);
    } catch (Exception e) {
      logger.info("Failed to delete refresh token. Token is invalid");
      throw new InvalidOrMissingRefreshTokenException(
          "Log out failed due to invalid or missing refresh token");
    }
  }

  private boolean isAuthenticatedUserOwnerOfRefreshToken(UUID refreshToken) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UUID userId = userService.getUserByName(authentication.getName()).getId();
    UUID issuedForId = refreshTokenService.getIssuedFor(refreshToken).getId();
    return userId == issuedForId;
  }
}
