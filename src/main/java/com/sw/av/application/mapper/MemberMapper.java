package com.sw.av.application.mapper;

import com.sw.av.domain.entities.Member;
import com.sw.av.application.dto.MemberDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    
    MemberDto toDto(Member employee);
    Member    toEntity(MemberDto employeeDto);
}
