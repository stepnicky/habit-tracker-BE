package com.habittracker.backend.feature.group.mapper;


import com.habittracker.backend.config.MapperConfig;
import com.habittracker.backend.feature.group.model.Group;
import com.habittracker.backend.feature.group.model.GroupDTO;
import com.habittracker.backend.feature.group.model.GroupMember;
import com.habittracker.backend.feature.group.model.GroupMemberDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface GroupMapper {

    GroupDTO groupToGroupDTO(Group group);

    @Mapping(target = "groupId", source = "group.id")
    GroupMemberDTO groupMemberToGroupMemberDTO(GroupMember groupMember);
}
