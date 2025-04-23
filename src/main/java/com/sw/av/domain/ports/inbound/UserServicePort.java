package com.sw.av.domain.ports.inbound;

import com.sw.av.application.dto.LoginRequestDto;
import com.sw.av.application.dto.UserDto;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserServicePort {
    
    UserDto login(LoginRequestDto loginRequestDto) throws UsernameNotFoundException;
    
    UserDto findByUsername(String username);
}
