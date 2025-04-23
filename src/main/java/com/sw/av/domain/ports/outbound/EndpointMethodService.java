package com.sw.av.domain.ports.outbound;

import com.sw.av.domain.entities.EndpointMethod;

public interface EndpointMethodService  extends ModelService<EndpointMethod, Long> {

    public EndpointMethod getEndpointMethod(String urlMethod, String app_name);
    
}
