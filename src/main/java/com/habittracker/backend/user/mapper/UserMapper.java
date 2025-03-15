package com.habittracker.backend.user.mapper;

import org.mapstruct.Mapper;
import com.habittracker.backend.config.MapperConfig;
import com.habittracker.backend.user.persistence.User;
import com.habittracker.backend.user.model.UserDTO;

import java.util.List;

@Mapper(config = MapperConfig.class)
public interface UserMapper {

    UserDTO entityToDTO(User user);

    List<UserDTO> entityToDTO(List<User> users);

    User dtoToEntity(UserDTO userDTO);
}
