package com.habittracker.backend.feature.group.model;

import java.util.List;
import java.util.UUID;

public record GroupDTO(UUID id,
                       String name,
                       List<GroupMemberDTO> members) {
}
