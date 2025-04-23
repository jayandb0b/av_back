package com.sw.av.infrastructure.persistence;

import java.util.List;
import com.sw.av.domain.entities.EndpointMethod;
import com.sw.av.domain.entities.MethodSecurity;
import com.sw.av.domain.repository.MethodSecurityRepository;
import com.sw.av.domain.entities.User;
import com.sw.av.domain.ports.outbound.MethodSecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class MethodSecurityServiceImpl extends ModelServiceImpl<MethodSecurity,Long, MethodSecurityRepository>
                                       implements MethodSecurityService {

    @Autowired
    private MethodSecurityRepository methodSecurityRepository;

    @Override
    public boolean checkAccess(EndpointMethod endpointMethod, User user) {
        if (endpointMethod != null && endpointMethod.getId() != null
                && user != null && user.getId() != null) {

            try {
                
                List<MethodSecurity> list = methodSecurityRepository.checkAccess(endpointMethod.getId(),
                                                                                 user.getId()
                                                                                 );
                if (list != null && list.size() > 0) {
                    return true;
                }

            } catch (Exception e) {
                log.error("🔥 🔥 🚒 ERROR IN MethodSecurityServiceImpl.checkAccess("+endpointMethod,toString()+","+user.toString()+")", e);
            }
        }

        return false;
    }

    @Override
    public boolean checkAccess(Long idEndpointMethod, Long idUser) {
        if (idEndpointMethod != null && idUser != null) {

            try {
                
                List<MethodSecurity> list = methodSecurityRepository.checkAccess(idEndpointMethod,
                                                                                 idUser
                                                                                 );
                if (list != null && list.size() > 0) {
                    return true;
                }

            } catch (Exception e) {
                log.error("🔥 🔥 🚒 ERROR IN MethodSecurityServiceImpl.checkAccess("+idEndpointMethod+","+idUser+")", e);
            }
        }

        return false;
    }

    
    
}
