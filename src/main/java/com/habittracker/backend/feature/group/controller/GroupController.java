package com.habittracker.backend.feature.group.controller;

import com.habittracker.backend.feature.group.model.CreateGroupRequest;
import com.habittracker.backend.feature.group.model.GroupDTO;
import com.habittracker.backend.feature.group.service.GroupService;
import com.habittracker.backend.feature.user.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public ResponseEntity<GroupDTO> createGroup(
            @RequestBody @Valid CreateGroupRequest request,
            @AuthenticationPrincipal User user) {
        GroupDTO group = groupService.createGroup(request.name(), user);
        return ResponseEntity.ok(group);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupDTO> getGroup(@PathVariable UUID groupId) {
        GroupDTO group = groupService.getGroupById(groupId);
        return ResponseEntity.ok(group);
    }

    @PostMapping("/{groupId}/join")
    public ResponseEntity<Void> joinGroup(@PathVariable UUID groupId,
                                          @AuthenticationPrincipal User user) {
        groupService.joinGroup(groupId, user);
        return ResponseEntity.ok().build();
    }
}
