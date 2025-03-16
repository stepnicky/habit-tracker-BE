package com.habittracker.backend.feature.group.service.impl;

import com.habittracker.backend.feature.group.exception.GroupNotFoundException;
import com.habittracker.backend.feature.group.mapper.GroupMapper;
import com.habittracker.backend.feature.group.model.Group;
import com.habittracker.backend.feature.group.model.GroupDTO;
import com.habittracker.backend.feature.group.model.GroupMember;
import com.habittracker.backend.feature.group.model.GroupRole;
import com.habittracker.backend.feature.group.persistence.GroupMemberRepository;
import com.habittracker.backend.feature.group.service.GroupService;
import com.habittracker.backend.feature.user.model.User;
import com.habittracker.backend.feature.group.persistence.GroupRepository;
import com.habittracker.backend.feature.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final GroupMemberRepository groupMemberRepository;
    private final UserRepository userRepository;

    @Transactional
    public void joinGroup(UUID groupId, User user) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException(groupId));

        if (groupMemberRepository.existsByUserAndGroup(user, group)) {
            throw new IllegalStateException("User is already a member of this group");
        }

        GroupMember newMember = new GroupMember(user, group, GroupRole.MEMBER);
        groupMemberRepository.save(newMember);
    }

    @Transactional
    public GroupDTO createGroup(String name, User owner) {
        Group group = new Group();
        group.setName(name);
        GroupMember groupOwner = new GroupMember(owner, group, GroupRole.OWNER);
        group.getMembers().add(groupOwner);
        group = groupRepository.save(group);
        return groupMapper.groupToGroupDTO(group);
    }

    public GroupDTO getGroupById(UUID groupId) {
        return groupRepository.findById(groupId)
                .map(groupMapper::groupToGroupDTO)
                .orElseThrow(() -> new GroupNotFoundException(groupId));
    }
}
