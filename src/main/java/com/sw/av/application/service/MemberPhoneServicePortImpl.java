package com.sw.av.application.service;

import com.sw.av.Constants;
import com.sw.av.application.dto.MemberPhoneDto;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sw.av.application.mapper.MemberPhoneMapper;
import com.sw.av.domain.entities.MemberPhone;
import com.sw.av.domain.ports.inbound.MemberPhoneServicePort;
import com.sw.av.domain.ports.outbound.MemberPhoneService;
import java.util.ArrayList;
import java.util.stream.Collectors;


@Service
@Slf4j
public class MemberPhoneServicePortImpl implements MemberPhoneServicePort{

    @Autowired
    private MemberPhoneService memberPhoneService;
    
    @Autowired
    private MemberPhoneMapper memberPhoneMapper;
    
    
    @Override
    public List<MemberPhoneDto> findBy(Long idMember) throws Exception {
        
        List<MemberPhoneDto> listEmployeePhoneDto = new ArrayList<>();
        
        try {
            
            List<MemberPhone> memberPhoneList = memberPhoneService.findBy(idMember);
            listEmployeePhoneDto = memberPhoneList.stream().map(memberPhoneMapper::toDto).collect(Collectors.toList());

        } catch (Exception ex) {
            log.error("ERROR IN MemberPhoneServicePortImpl.findBy(" + idMember + ")", ex);
            throw new Exception(ex);
        }
        
        return listEmployeePhoneDto;
    }
    
    @Override
    public MemberPhoneDto findById(Long id) {
        return memberPhoneMapper.toDto(memberPhoneService.getById(id));
    }

    @Override
    public MemberPhoneDto save(MemberPhoneDto memberPhoneDto, Long idUser) {
        MemberPhone memberPhone = memberPhoneMapper.toEntity(memberPhoneDto);
        memberPhone.setDateCreated(new Date());
        memberPhone.setDateUpdated(new Date());
        memberPhone.setUserCreated(idUser);
        memberPhone.setUserUpdated(idUser);
        memberPhone.setActive(Constants.ACTIVE_Y);
        
        return memberPhoneMapper.toDto(memberPhoneService.save(memberPhone));
    }

    @Override
    public MemberPhoneDto merge(Long id, MemberPhoneDto memberPhoneDto, Long idUser) throws Exception {
        
        MemberPhone memberPhone = memberPhoneService.getById(id);
        
        if(memberPhone == null){
            memberPhoneDto = save(memberPhoneDto,idUser);
        }
        else{
            memberPhone = memberPhoneMapper.toEntity(memberPhoneDto);
            memberPhone.setId(memberPhoneDto.getId());
            memberPhone.setDateUpdated(new Date());
            memberPhone.setUserUpdated(idUser);
            memberPhone = memberPhoneService.save(memberPhone);
            memberPhoneDto = memberPhoneMapper.toDto(memberPhone);
        }
        
        return  memberPhoneDto;
    }
 
    
}
