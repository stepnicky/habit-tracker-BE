package com.habittracker.backend.feature.user.controller;

import com.habittracker.backend.feature.user.model.RequestChangePassword;
import com.habittracker.backend.feature.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.habittracker.backend.feature.user.model.User;
import com.habittracker.backend.feature.user.model.UserDTO;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserController {
  private final UserService userService;

  @GetMapping
  public ResponseEntity<List<UserDTO>> getUsers() {
    List<UserDTO> userList = userService.getUsers();
    return ResponseEntity.ok().body(userList);
  }

  @PostMapping
  public ResponseEntity<Object> addUser(@Valid @RequestBody User user) {
    log.debug("Starting to add user");
    userService.addUser(user);
    log.info("User was added successfully");
    return ResponseEntity.ok().build();
  }

  @PatchMapping("change-password")
  public ResponseEntity<Object> updateUserPassword(
      @Valid @RequestBody RequestChangePassword request) {
    log.debug("Starting to update password");
    userService.updateUserPassword(request.oldPassword(), request.newPassword());
    log.info("Password was updated successfully");
    return ResponseEntity.ok().build();
  }

  @GetMapping("/logged")
  public User getCurrentUser(@AuthenticationPrincipal User user) {
    return user;
  }

  @PatchMapping("language/{newLanguage}")
  public ResponseEntity<Void> updateUserLanguage(@PathVariable String newLanguage,
                                                 @AuthenticationPrincipal User currentUser) {
    userService.updateLanguage(currentUser, newLanguage);
    return ResponseEntity.ok().build();
  }
}
