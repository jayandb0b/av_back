package com.sw.av.application.mapper.impl;

import com.sw.av.domain.entities.Member;
import com.sw.av.application.dto.MemberDto;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.stereotype.Component;
import com.sw.av.application.mapper.MemberMapper;

@Component
public class MemberMapperImpl implements MemberMapper{

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    
    @Override
    public MemberDto toDto(Member member) {
        
        if(member == null)
        {
            return null;
        }
        
        MemberDto memberDto = new MemberDto();
        memberDto.setId(member.getId());
        memberDto.setSurnameName(member.getSurnameName());
        memberDto.setDni(member.getDni());
        memberDto.setEmail(member.getEmail());
        memberDto.setComments(member.getComments());
        memberDto.setMemberNumber(member.getMemberNumber());
        memberDto.setActive(member.getActive());
        memberDto.setDateUpdate(member.getDateUpdated()!=null?member.getDateUpdated().getTime():null);
        return memberDto;
    }

    @Override
    public Member toEntity(MemberDto memberDto) {
        
        if(memberDto == null)
        {
            return null;
        }
        
        Member member = new Member();
        member.setId(memberDto.getId());
        member.setSurnameName(memberDto.getSurnameName()!=null?memberDto.getSurnameName().toUpperCase():null);
        member.setDni(memberDto.getDni()!=null?memberDto.getDni().toUpperCase():null);
        member.setEmail(memberDto.getEmail()!=null?memberDto.getEmail().toLowerCase():null);
        member.setComments(memberDto.getComments());
        member.setMemberNumber(memberDto.getMemberNumber());
        member.setActive(memberDto.getActive());
        
        return member;
    }
    
}
