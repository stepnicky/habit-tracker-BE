package com.habittracker.backend.user.service;

import com.habittracker.backend.user.model.UserDTO;
import com.habittracker.backend.user.persistence.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

  User getUserByName(String username);

  void addUser(User user);

  void updateUserPassword(String oldPassword, String newPassword);

  List<UserDTO> getUsers();

  void updateLanguage(User currentUser, String newLanguage);
}