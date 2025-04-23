package com.sw.av.domain.ports.inbound;

import com.sw.av.application.dto.EndpointMethodDto;

public interface EndpointMethodServicePort {

    public EndpointMethodDto getEndpointMethod(String urlMethod, String app_name);
    
}
