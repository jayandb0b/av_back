package com.sw.av.application.mapper;

import com.sw.av.application.dto.MemberPhoneDto;
import com.sw.av.domain.entities.MemberPhone;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberPhoneMapper {
    
    MemberPhoneDto toDto(MemberPhone memberPhone);
    MemberPhone    toEntity(MemberPhoneDto employeePhoneDto);
}
