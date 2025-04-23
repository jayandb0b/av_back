package com.sw.av.application.service;

import com.sw.av.Constants;
import com.sw.av.application.dto.MemberPaymentDto;
import com.sw.av.application.mapper.MemberPaymentMapper;
import com.sw.av.domain.entities.MemberPayment;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sw.av.domain.ports.inbound.MemberPaymentServicePort;
import com.sw.av.domain.ports.outbound.MemberPaymentService;
import java.util.ArrayList;
import java.util.stream.Collectors;


@Service
@Slf4j
public class MemberPaymentServicePortImpl implements MemberPaymentServicePort{

    @Autowired
    private MemberPaymentService memberPaymentService;
    
    @Autowired
    private MemberPaymentMapper memberPaymentMapper;
    
    
    @Override
    public List<MemberPaymentDto> findBy(Long idMember) throws Exception {
        
        List<MemberPaymentDto> listMemberPaymentDto = new ArrayList<>();
        
        try {
            
            List<MemberPayment> memberPaymentList = memberPaymentService.findBy(idMember);
            listMemberPaymentDto = memberPaymentList.stream().map(memberPaymentMapper::toDto).collect(Collectors.toList());

        } catch (Exception ex) {
            log.error("ERROR IN MemberPaymentServicePortImpl.findBy(" + idMember + ")", ex);
            throw new Exception(ex);
        }
        
        return listMemberPaymentDto;
    }
    
    @Override
    public MemberPaymentDto findById(Long id) {
        return memberPaymentMapper.toDto(memberPaymentService.getById(id));
    }

    @Override
    public MemberPaymentDto save(MemberPaymentDto memberPaymentDto, Long idUser) {
        MemberPayment memberPayment = memberPaymentMapper.toEntity(memberPaymentDto);
        memberPayment.setDateCreated(new Date());
        memberPayment.setDateUpdated(new Date());
        memberPayment.setUserCreated(idUser);
        memberPayment.setUserUpdated(idUser);
        memberPayment.setActive(Constants.ACTIVE_Y);
        
        return memberPaymentMapper.toDto(memberPaymentService.save(memberPayment));
    }

    @Override
    public MemberPaymentDto merge(Long id, MemberPaymentDto memberPaymentDto, Long idUser) throws Exception {
        
        MemberPayment memberPayment = memberPaymentService.getById(id);
        
        if(memberPayment == null){
            memberPaymentDto = save(memberPaymentDto,idUser);
        }
        else{
            memberPayment = memberPaymentMapper.toEntity(memberPaymentDto);
            memberPayment.setId(memberPaymentDto.getId());
            memberPayment.setDateUpdated(new Date());
            memberPayment.setUserUpdated(idUser);
            memberPayment = memberPaymentService.save(memberPayment);
            memberPaymentDto = memberPaymentMapper.toDto(memberPayment);
        }
        
        return  memberPaymentDto;
    }
 
    
}
