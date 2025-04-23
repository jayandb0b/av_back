package com.sw.av.domain.ports.inbound;

import com.sw.av.application.dto.MemberPaymentDto;
import java.util.List;


public interface MemberPaymentServicePort {
    
    List<MemberPaymentDto> findBy(Long idMemeber) throws Exception;
    
    MemberPaymentDto findById(Long id) throws Exception;
    
    MemberPaymentDto save(MemberPaymentDto memberPaymentDto, Long idUser) throws Exception;
    
    MemberPaymentDto merge(Long id, MemberPaymentDto memberPaymentDto, Long idUser) throws Exception;
}
