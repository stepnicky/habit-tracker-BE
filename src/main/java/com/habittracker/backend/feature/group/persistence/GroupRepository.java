package com.habittracker.backend.feature.group.persistence;

import com.habittracker.backend.feature.group.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {
}
