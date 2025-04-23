package com.sw.av.application.mapper.impl;

import com.sw.av.application.dto.UserDto;
import com.sw.av.application.mapper.UserMapper;
import com.sw.av.domain.entities.User;
import java.text.SimpleDateFormat;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper{

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    
    @Override
    public UserDto toDto(User user) {
        
        if(user == null)
        {
            return null;
        }
        
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setRoles(user.getRoles());
        
        return userDto;
    }

    @Override
    public User toEntity(UserDto userDto) {
        
        if(userDto == null)
        {
            return null;
        }
        
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setRoles(userDto.getRoles());
        
        return user;
    }
    
    
    
}
