package com.habittracker.backend.user.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_role")
public class UserRole {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "description", nullable = false)
    private String description;
}

