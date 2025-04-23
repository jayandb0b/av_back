package com.sw.av.domain.ports.outbound;


import com.sw.av.domain.entities.EndpointMethod;
import com.sw.av.domain.entities.MethodSecurity;
import com.sw.av.domain.entities.User;

public interface MethodSecurityService extends ModelService<MethodSecurity, Long> {
     boolean checkAccess(EndpointMethod endpointMethod, User user) ;
     
     boolean checkAccess(Long idEndpointMethod, Long idUser) ;

}
