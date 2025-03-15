package com.habittracker.backend.user.exception;

import com.habittracker.backend.exception.InvalidDataException;

public class UserEmailExistsException extends InvalidDataException {

  public UserEmailExistsException(String email) {
    super(String.format("Account with given email: %s already exists", email));
  }
}
