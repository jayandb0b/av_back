package com.sw.av.domain.ports.inbound;

import com.sw.av.application.dto.MemberPhoneDto;
import java.util.List;


public interface MemberPhoneServicePort {
    
    List<MemberPhoneDto> findBy(Long idMemeber) throws Exception;
    
    MemberPhoneDto findById(Long id) throws Exception;
    
    MemberPhoneDto save(MemberPhoneDto employPhoneDto, Long idUser) throws Exception;
    
    MemberPhoneDto merge(Long id, MemberPhoneDto memberPhoneDto, Long idUser) throws Exception;
}
