package com.habittracker.backend.feature.user.service;

import com.habittracker.backend.feature.user.model.UserDTO;
import com.habittracker.backend.feature.user.persistence.User;

import java.util.List;

public interface UserService {

  User getUserByName(String username);

  void addUser(User user);

  void updateUserPassword(String oldPassword, String newPassword);

  List<UserDTO> getUsers();

  void updateLanguage(User currentUser, String newLanguage);
}