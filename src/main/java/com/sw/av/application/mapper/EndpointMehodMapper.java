package com.sw.av.application.mapper;

import com.sw.av.application.dto.EndpointMethodDto;
import com.sw.av.domain.entities.EndpointMethod;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EndpointMehodMapper {
    EndpointMethodDto toDto(EndpointMethod endpointMethod);
    EndpointMethod    toEntity(EndpointMethodDto endpointMehodDto);
}
