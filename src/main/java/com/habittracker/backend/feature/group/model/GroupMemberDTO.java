package com.habittracker.backend.feature.group.model;

import com.habittracker.backend.feature.user.model.UserDTO;

import java.util.UUID;

public record GroupMemberDTO(UserDTO user,
                             GroupRole role,
                             UUID groupId) {
}
