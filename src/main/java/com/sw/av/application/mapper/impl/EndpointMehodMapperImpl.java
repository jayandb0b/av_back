package com.sw.av.application.mapper.impl;

import com.sw.av.application.dto.EndpointMethodDto;
import com.sw.av.application.mapper.EndpointMehodMapper;
import com.sw.av.domain.entities.EndpointMethod;
import java.text.SimpleDateFormat;
import org.springframework.stereotype.Component;

@Component
public class EndpointMehodMapperImpl implements EndpointMehodMapper{

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    
    @Override
    public EndpointMethodDto toDto(EndpointMethod endpointMethod) {
        
        if(endpointMethod == null)
        {
            return null;
        }
        
        EndpointMethodDto endpointMethodDto = new EndpointMethodDto();
        endpointMethodDto.setId(endpointMethod.getId());
        endpointMethodDto.setNameMethod(endpointMethod.getNameMethod());
        endpointMethodDto.setServiceName(endpointMethod.getServiceName());
        
        return endpointMethodDto;
    }

    @Override
    public EndpointMethod toEntity(EndpointMethodDto endpointMethodDto) {
        
        if(endpointMethodDto == null)
        {
            return null;
        }
        
        EndpointMethod endpointMethod = new EndpointMethod();
        endpointMethod.setId(endpointMethodDto.getId());
        endpointMethod.setNameMethod(endpointMethodDto.getNameMethod());
        endpointMethod.setServiceName(endpointMethodDto.getServiceName());
        
        return endpointMethod;
    }
    
    
    
}
