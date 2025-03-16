package com.habittracker.backend.feature.group.persistence;

import com.habittracker.backend.feature.group.model.Group;
import com.habittracker.backend.feature.group.model.GroupMember;
import com.habittracker.backend.feature.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, UUID> {
    boolean existsByUserAndGroup(User user, Group group);
}