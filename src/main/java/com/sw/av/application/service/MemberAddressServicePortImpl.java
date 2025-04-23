package com.sw.av.application.service;

import com.sw.av.Constants;
import com.sw.av.application.dto.MemberAddressDto;
import com.sw.av.application.mapper.MemberAddressMapper;
import com.sw.av.domain.entities.MemberAddress;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sw.av.domain.ports.inbound.MemberAddressServicePort;
import com.sw.av.domain.ports.outbound.MemberAddressService;
import java.util.ArrayList;
import java.util.stream.Collectors;


@Service
@Slf4j
public class MemberAddressServicePortImpl implements MemberAddressServicePort{

    @Autowired
    private MemberAddressService memberAddressService;
    
    @Autowired
    private MemberAddressMapper memberAddressMapper;
    
    
    @Override
    public List<MemberAddressDto> findBy(Long idMember) throws Exception {
        
        List<MemberAddressDto> listMemberAddressDto = new ArrayList<>();
        
        try {
            
            List<MemberAddress> memberAddressList = memberAddressService.findBy(idMember);
            listMemberAddressDto = memberAddressList.stream().map(memberAddressMapper::toDto).collect(Collectors.toList());

        } catch (Exception ex) {
            log.error("ERROR IN MemberAddressServicePortImpl.findBy(" + idMember + ")", ex);
            throw new Exception(ex);
        }
        
        return listMemberAddressDto;
    }
    
    @Override
    public MemberAddressDto findById(Long id) {
        return memberAddressMapper.toDto(memberAddressService.getById(id));
    }

    @Override
    public MemberAddressDto save(MemberAddressDto memberAddressDto, Long idUser) {
        MemberAddress memberAddress = memberAddressMapper.toEntity(memberAddressDto);
        memberAddress.setDateCreated(new Date());
        memberAddress.setDateUpdated(new Date());
        memberAddress.setUserCreated(idUser);
        memberAddress.setUserUpdated(idUser);
        memberAddress.setActive(Constants.ACTIVE_Y);
        
        return memberAddressMapper.toDto(memberAddressService.save(memberAddress));
    }

    @Override
    public MemberAddressDto merge(Long id, MemberAddressDto memberAddressDto, Long idUser) throws Exception {
        
        MemberAddress memberAddress = memberAddressService.getById(id);
        
        if(memberAddress == null){
            memberAddressDto = save(memberAddressDto,idUser);
        }
        else{
            memberAddress = memberAddressMapper.toEntity(memberAddressDto);
            memberAddress.setId(memberAddressDto.getId());
            memberAddress.setDateUpdated(new Date());
            memberAddress.setUserUpdated(idUser);
            memberAddress = memberAddressService.save(memberAddress);
            memberAddressDto = memberAddressMapper.toDto(memberAddress);
        }
        
        return  memberAddressDto;
    }
 
}
