package com.sw.av.application.mapper.impl;

import com.sw.av.application.dto.MemberAddressDto;
import com.sw.av.application.mapper.MemberAddressMapper;
import com.sw.av.domain.entities.MemberAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class MemberAddressMapperImpl implements MemberAddressMapper{

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    
    @Override
    public MemberAddressDto toDto(MemberAddress memberAddress) {
        
        if(memberAddress == null)
        {
            return null;
        }
        
        MemberAddressDto memberAddressDto = new MemberAddressDto();
        memberAddressDto.setId(memberAddress.getId());
        memberAddressDto.setMemberId(memberAddress.getMemberId());
        memberAddressDto.setStreet(memberAddress.getStreet());
        memberAddressDto.setPostalCode(memberAddress.getPostalCode());
        memberAddressDto.setLocality(memberAddress.getLocality());
        memberAddressDto.setProvince(memberAddress.getProvince());
        memberAddressDto.setIsDefault(memberAddress.getIsDefault());
        memberAddressDto.setActive(memberAddress.getActive());
        memberAddressDto.setDateUpdated(memberAddress.getDateUpdated()!=null?memberAddress.getDateUpdated().getTime():null);
        return memberAddressDto;
    }

    @Override
    public MemberAddress toEntity(MemberAddressDto memberAddressDto) {
        
        if(memberAddressDto == null)
        {
            return null;
        }
        
        MemberAddress memberAddress = new MemberAddress();
        
        memberAddress.setId(memberAddressDto.getId());
        memberAddress.setMemberId(memberAddressDto.getMemberId());
        memberAddress.setStreet(memberAddressDto.getStreet());
        memberAddress.setPostalCode(memberAddressDto.getPostalCode());
        memberAddress.setLocality(memberAddressDto.getLocality());
        memberAddress.setProvince(memberAddressDto.getProvince());
        memberAddress.setIsDefault(memberAddressDto.getIsDefault());
        memberAddress.setActive(memberAddressDto.getActive());
        memberAddress.setDateUpdated(memberAddressDto.getDateUpdated()!=null?new Date(memberAddressDto.getDateUpdated()):null);
                
        return memberAddress;
    }
    
}
