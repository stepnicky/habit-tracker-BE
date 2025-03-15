package com.habittracker.backend.auth.service;

import com.habittracker.backend.auth.model.AuthRequest;
import com.habittracker.backend.auth.model.AuthResponse;

public interface AuthService {

  AuthResponse authenticate(AuthRequest request);

  AuthResponse refreshAccessToken(String refreshToken);

  void logOut(String refreshToken);

}
