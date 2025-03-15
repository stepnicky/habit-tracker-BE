package com.habittracker.backend.user.service.impl;

import com.habittracker.backend.user.exception.InvalidPasswordException;
import com.habittracker.backend.user.exception.InvalidPrincipalException;
import com.habittracker.backend.user.exception.UserEmailExistsException;
import com.habittracker.backend.user.exception.UserNotAuthenticatedException;
import com.habittracker.backend.user.exception.UsernameExistsException;
import com.habittracker.backend.user.mapper.UserMapper;
import com.habittracker.backend.user.model.Language;
import com.habittracker.backend.user.model.UserDTO;
import com.habittracker.backend.user.persistence.User;
import com.habittracker.backend.user.persistence.UserRepository;
import com.habittracker.backend.user.service.UserService;
import com.habittracker.backend.user.validation.PasswordConstraintValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final PasswordConstraintValidator passwordConstraintValidator;

  private final UserMapper userMapper;
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Override
  public List<UserDTO> getUsers() {
    List<User> users = userRepository.findAll();
    return userMapper.entityToDTO(users);
  }

  @Override
  public User getUserByName(String username) {
    Optional<User> user = userRepository.findUserByUsername(username);
    if (user.isEmpty()) {
      throw new UsernameNotFoundException("User with given username does not exist.");
    }
    return user.get();
  }

  @Override
  @Transactional
  public void addUser(User user) {

    if (isUserWithEmail(user.getEmail())) {
      throw new UserEmailExistsException(user.getEmail());
    }
    if (isUserWithUsername(user.getUsername())) {
      throw new UsernameExistsException(user.getUsername());
    }
    if (!isPasswordValid(user.getPassword())) {
      throw new InvalidPasswordException("Invalid password");
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
  }

  @Override
  @Transactional
  public void updateUserPassword(String oldPassword, String newPassword) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User user = (User) authentication.getPrincipal();
    if (!isUserPasswordCorrect(user.getId(), user.getPassword(), oldPassword)) {
      throw new InvalidPasswordException("Old password is incorrect");
    }
    if (!isPasswordValid(newPassword)) {
      logger.debug("Password validation failed");
      throw new InvalidPasswordException("New password is invalid");
    }
    user.setPassword(passwordEncoder.encode(newPassword));
    userRepository.save(user);
  }

  @Transactional
  public void updateLanguage(User currentUser, String newLanguage) {
      Language language = Language.fromCode(newLanguage);
      currentUser.setLanguage(language);
      userRepository.save(currentUser);
  }

  private boolean isUserPasswordCorrect(UUID id, String userPassword, String userOldPassword) {
    return userRepository.existsById(id) && passwordEncoder.matches(userOldPassword, userPassword);
  }

  private boolean isUserWithEmail(String email) {
    return userRepository.existsByEmailContainingIgnoreCase(email);
  }

  private boolean isUserWithUsername(String userName) {
    return userRepository.existsByUsernameContainingIgnoreCase(userName);
  }

  private boolean isPasswordValid(String password) {
    return passwordConstraintValidator.isValid(password);
  }

  private UUID getCurrentUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      throw new UserNotAuthenticatedException("User not authenticated");
    }

    Object principal = authentication.getPrincipal();
    if (!(principal instanceof UserDetails userDetails)) {
      throw new InvalidPrincipalException("Invalid principal");
    }

    Optional<User> user = userRepository.findUserByUsername(userDetails.getUsername());

    return user.get().getId();
  }
}