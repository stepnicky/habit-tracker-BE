package com.habittracker.backend.feature.group.service;

import com.habittracker.backend.feature.group.model.GroupDTO;
import com.habittracker.backend.feature.user.model.User;

import java.util.UUID;

public interface GroupService {
    void joinGroup(UUID groupId, User user);
    GroupDTO createGroup(String name, User owner);
    GroupDTO getGroupById(UUID groupId);
}
