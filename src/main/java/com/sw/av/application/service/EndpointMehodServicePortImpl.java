package com.sw.av.application.service;

import com.sw.av.application.dto.EndpointMethodDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sw.av.domain.ports.outbound.EndpointMethodService;
import com.sw.av.domain.ports.inbound.EndpointMethodServicePort;
import com.sw.av.application.mapper.EndpointMehodMapper;

@Service
@Slf4j
public class EndpointMehodServicePortImpl implements EndpointMethodServicePort{

    @Autowired
    private EndpointMethodService endpointMethodService;
    
    @Autowired
    private EndpointMehodMapper endpointMethodMapper;

    @Override
    public EndpointMethodDto getEndpointMethod(String urlMethod, String app_name) {
        return endpointMethodMapper.toDto(endpointMethodService.getEndpointMethod(urlMethod, app_name));
    }
     
}
