package com.habittracker.backend.feature.group.service;

import com.habittracker.backend.feature.group.model.Group;
import com.habittracker.backend.feature.group.model.GroupMember;
import com.habittracker.backend.feature.group.model.GroupRole;
import com.habittracker.backend.feature.group.persistence.GroupMemberRepository;
import com.habittracker.backend.feature.user.persistence.User;
import com.habittracker.backend.feature.group.persistence.GroupRepository;
import com.habittracker.backend.feature.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final UserRepository userRepository;

    @Transactional
    public Group createGroup(String name, User owner) {
        Group group = new Group();
        group.setName(name);

        GroupMember groupOwner = new GroupMember(owner, group, GroupRole.OWNER);
        group.getMembers().add(groupOwner);

        return groupRepository.save(group);
    }

    public Group getGroupById(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
    }
}
