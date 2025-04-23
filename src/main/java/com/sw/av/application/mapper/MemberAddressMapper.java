package com.sw.av.application.mapper;

import com.sw.av.application.dto.MemberAddressDto;
import com.sw.av.domain.entities.MemberAddress;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberAddressMapper {
    
    MemberAddressDto toDto(MemberAddress memberAddress);
    MemberAddress    toEntity(MemberAddressDto memberAddressDto);
}
