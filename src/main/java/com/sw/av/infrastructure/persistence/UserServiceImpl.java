package com.sw.av.infrastructure.persistence;

import com.sw.av.domain.entities.User;
import com.sw.av.domain.ports.outbound.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sw.av.domain.repository.UserRepository;

//extends GsModelServiceImpl<ParamAplicacion,String, ParamApplicationDao>
@Service
//@EnableJpaRepositories({"com.gs.commons.model.dao"})
//@Import({ParamApplication.class})
@Slf4j
public class UserServiceImpl extends ModelServiceImpl<User, Long, UserRepository> implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        if (username != null) {

            try {
                User user = userRepository.getByUsername(username);
                return user;

            } catch (Exception e) {
                log.error("🔥 🔥 🚒 ERROR IN UserServiceImpl.getByUsername(" + username + ")", e);
            }
        }

        return null;
    }
        
}
