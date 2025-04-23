package com.sw.av.application.mapper.impl;

import com.sw.av.domain.entities.Member;
import com.sw.av.application.dto.MemberDto;
import com.sw.av.application.dto.MemberPhoneDto;
import com.sw.av.application.mapper.MemberPhoneMapper;
import com.sw.av.domain.entities.MemberPhone;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class MemberPhoneMapperImpl implements MemberPhoneMapper{

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    
    @Override
    public MemberPhoneDto toDto(MemberPhone memberPhone) {
        
        if(memberPhone == null)
        {
            return null;
        }
        
        MemberPhoneDto memberPhoneDto = new MemberPhoneDto();
        memberPhoneDto.setId(memberPhone.getId());
        memberPhoneDto.setMemberId(memberPhone.getMemberId());
        memberPhoneDto.setPhone(memberPhone.getPhone());
        memberPhoneDto.setIsDefault(memberPhone.getIsDefault());
        memberPhoneDto.setActive(memberPhone.getActive());
        memberPhoneDto.setDateUpdated(memberPhone.getDateUpdated()!=null?memberPhone.getDateUpdated().getTime():null);
        return memberPhoneDto;
    }

    @Override
    public MemberPhone toEntity(MemberPhoneDto memberPhoneDto) {
        
        if(memberPhoneDto == null)
        {
            return null;
        }
        
        MemberPhone memberPhone = new MemberPhone();
        memberPhone.setId(memberPhoneDto.getId());
        memberPhone.setMemberId(memberPhoneDto.getMemberId());
        memberPhone.setPhone(memberPhoneDto.getPhone());
        memberPhone.setIsDefault(memberPhoneDto.getIsDefault());
        memberPhone.setActive(memberPhoneDto.getActive());
        memberPhone.setDateUpdated(memberPhoneDto.getDateUpdated()!=null?new Date(memberPhoneDto.getDateUpdated()):null);
        
        return memberPhone;
    }
    
}
