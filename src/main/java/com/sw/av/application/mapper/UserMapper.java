package com.sw.av.application.mapper;

import com.sw.av.application.dto.UserDto;
import com.sw.av.domain.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    
    UserDto toDto(User user);
    User    toEntity(UserDto userDto);
}
