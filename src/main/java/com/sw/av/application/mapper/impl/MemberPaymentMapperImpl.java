package com.sw.av.application.mapper.impl;

import com.sw.av.application.dto.MemberPaymentDto;
import com.sw.av.application.mapper.MemberPaymentMapper;
import com.sw.av.domain.entities.MemberPayment;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class MemberPaymentMapperImpl implements MemberPaymentMapper{

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    
    @Override
    public MemberPaymentDto toDto(MemberPayment memberPayment) {
        
        if(memberPayment == null)
        {
            return null;
        }
        
        MemberPaymentDto memberPaymentDto = new MemberPaymentDto();
        memberPaymentDto.setId(memberPayment.getId());
        memberPaymentDto.setMemberId(memberPayment.getMemberId());
        memberPaymentDto.setAmount(memberPayment.getAmount());
        memberPaymentDto.setComments(memberPayment.getComments());
        memberPaymentDto.setPaymentDate(memberPayment.getPaymentDate()!=null?memberPayment.getPaymentDate().getTime():null);
        memberPaymentDto.setActive(memberPayment.getActive());
        memberPaymentDto.setDateUpdated(memberPayment.getDateUpdated()!=null?memberPayment.getDateUpdated().getTime():null);
        return memberPaymentDto;
    }

    @Override
    public MemberPayment toEntity(MemberPaymentDto memberPaymentDto) {
        
        if(memberPaymentDto == null)
        {
            return null;
        }
        
        MemberPayment memberPayment = new MemberPayment();
        memberPayment.setId(memberPaymentDto.getId());
        memberPayment.setMemberId(memberPaymentDto.getMemberId());
        memberPayment.setAmount(memberPaymentDto.getAmount());
        memberPayment.setComments(memberPaymentDto.getComments());
        memberPayment.setPaymentDate(memberPaymentDto.getPaymentDate()!=null?(new Date(memberPaymentDto.getPaymentDate())):null);
        memberPayment.setActive(memberPaymentDto.getActive());
        memberPayment.setDateUpdated(memberPaymentDto.getDateUpdated()!=null?(new Date(memberPaymentDto.getDateUpdated())):null);
        return memberPayment;
    }
    
}
