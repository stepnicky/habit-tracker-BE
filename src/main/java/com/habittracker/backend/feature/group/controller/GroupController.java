package com.habittracker.backend.feature.group.controller;

import com.habittracker.backend.feature.group.model.CreateGroupRequest;
import com.habittracker.backend.feature.group.model.Group;
import com.habittracker.backend.feature.group.service.GroupService;
import com.habittracker.backend.feature.user.persistence.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GroupController {

    private final GroupService groupService;

    @PostMapping("/groups")
    public ResponseEntity<Group> createGroup(
            @RequestBody @Valid CreateGroupRequest request,
            @AuthenticationPrincipal User user) {

        Group group = groupService.createGroup(request.name(), user);

        return ResponseEntity.ok(group);
    }
}
