package com.habittracker.backend.feature.user.exception;

import com.habittracker.backend.exception.InvalidDataException;

public class InvalidPasswordException extends InvalidDataException {

  public InvalidPasswordException(String message) {
    super(message);
  }

}
