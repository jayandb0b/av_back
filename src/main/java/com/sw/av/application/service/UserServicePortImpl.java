package com.sw.av.application.service;

import com.sw.av.application.dto.LoginRequestDto;
import com.sw.av.application.dto.UserDto;
import com.sw.av.application.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sw.av.domain.ports.inbound.UserServicePort;
import com.sw.av.domain.ports.outbound.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@Slf4j
public class UserServicePortImpl implements UserServicePort{

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public UserDto login(LoginRequestDto loginRequestDto) throws UsernameNotFoundException {
                
        UserDto userDto = userMapper.toDto(userService.findByUsername(loginRequestDto.getLogin()));
        
        if(userDto !=null && passwordEncoder.matches(loginRequestDto.getPassword(), userDto.getPassword()))
        {   
            return userDto;
        }
        else{
            throw new UsernameNotFoundException("User not found");
        }
    }

    @Override
    public UserDto findByUsername(String username){
        return userMapper.toDto(userService.findByUsername(username));
    }
    
}
