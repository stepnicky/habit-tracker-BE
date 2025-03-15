package com.habittracker.backend.user.exception;

import com.habittracker.backend.exception.InvalidDataException;

public class InvalidPasswordException extends InvalidDataException {

  public InvalidPasswordException(String message) {
    super(message);
  }

}
