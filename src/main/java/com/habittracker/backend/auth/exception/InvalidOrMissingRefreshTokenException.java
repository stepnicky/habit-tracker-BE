package com.habittracker.backend.auth.exception;

import com.habittracker.backend.exception.InvalidDataException;

public class InvalidOrMissingRefreshTokenException extends InvalidDataException {

  public InvalidOrMissingRefreshTokenException(String message) {
    super(message);
  }
}
