package com.habittracker.backend.user.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findUserByUsername(String username);

    Boolean existsByEmailContainingIgnoreCase(String email);

    Boolean existsByUsernameContainingIgnoreCase(String username);
}
