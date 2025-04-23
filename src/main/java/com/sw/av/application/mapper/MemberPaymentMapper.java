package com.sw.av.application.mapper;

import com.sw.av.application.dto.MemberPaymentDto;
import com.sw.av.domain.entities.MemberPayment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberPaymentMapper {
    
    MemberPaymentDto toDto(MemberPayment memberPayment);
    MemberPayment    toEntity(MemberPaymentDto memberPaymentDto);
}
