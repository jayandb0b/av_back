package com.sw.av.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sw.av.domain.ports.inbound.MethodSecurityServicePort;
import com.sw.av.domain.ports.outbound.MethodSecurityService;

@Service
@Slf4j
public class MethodSecurityServicePortImpl implements MethodSecurityServicePort{

    @Autowired
    private MethodSecurityService endpointMethodService;

    @Override
    public boolean checkAccess(Long idEndpointMethod, Long idUser) {
        return endpointMethodService.checkAccess(idEndpointMethod, idUser);
    }
     
}
