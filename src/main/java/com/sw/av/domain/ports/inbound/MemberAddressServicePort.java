package com.sw.av.domain.ports.inbound;

import com.sw.av.application.dto.MemberAddressDto;
import java.util.List;


public interface MemberAddressServicePort {
    
    List<MemberAddressDto> findBy(Long idMemeber) throws Exception;
    
    MemberAddressDto findById(Long id) throws Exception;
    
    MemberAddressDto save(MemberAddressDto employPhoneDto, Long idUser) throws Exception;
    
    MemberAddressDto merge(Long id, MemberAddressDto memberAddressDto, Long idUser) throws Exception;
}
