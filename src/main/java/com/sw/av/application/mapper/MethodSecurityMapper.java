package com.sw.av.application.mapper;


import com.sw.av.application.dto.MethodSecurityDto;
import com.sw.av.domain.entities.MethodSecurity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MethodSecurityMapper {
    
    MethodSecurityDto toDto(MethodSecurity methodSecurity);
    MethodSecurity    toEntity(MethodSecurityDto endpointMehodDto);
}
