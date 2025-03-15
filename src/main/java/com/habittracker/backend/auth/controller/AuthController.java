package com.habittracker.backend.auth.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.habittracker.backend.auth.model.AuthRequest;
import com.habittracker.backend.auth.model.AuthResponse;
import com.habittracker.backend.auth.service.AuthService;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {
    logger.debug("Started authentication process");
    AuthResponse response = authService.authenticate(request);
    logger.info("Authentication completed");
    return ResponseEntity.ok()
        .header(HttpHeaders.AUTHORIZATION, response.accessToken())
        .body(response);
  }

  @GetMapping("/refresh")
  public ResponseEntity<AuthResponse> refreshAccessToken(
      @RequestParam String refreshToken) {
    logger.debug("Started token regeneration process");
    AuthResponse response = authService.refreshAccessToken(refreshToken);
    logger.info("Token was successfully regenerated");
    return ResponseEntity.ok()
        .header(HttpHeaders.AUTHORIZATION, response.accessToken())
        .body(response);
  }

  @GetMapping("/logout")
  public ResponseEntity<Object> logout(@RequestParam String refreshToken
  ) {
    logger.debug("Started logout process");
    authService.logOut(refreshToken);
    logger.info("Logout completed");
    return ResponseEntity.ok().build();
  }
}
