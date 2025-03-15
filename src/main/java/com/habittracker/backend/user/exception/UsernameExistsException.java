package com.habittracker.backend.user.exception;

import com.habittracker.backend.exception.InvalidDataException;

public class UsernameExistsException extends InvalidDataException {

  public UsernameExistsException(String username) {
    super(String.format("Account with given username: %s already exists", username));
  }


}
