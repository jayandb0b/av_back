package com.sw.av.domain.ports.outbound;


import com.sw.av.domain.entities.User;

public interface UserService extends ModelService<User, Long> {
    
    User findByUsername(String username);
    
}
