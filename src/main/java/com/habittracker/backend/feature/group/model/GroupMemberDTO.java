package com.habittracker.backend.feature.group.model;

public record GroupMemberDTO(Long userId,
                             String username,
                             GroupRole role) {
}
