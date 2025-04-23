package com.sw.av.domain.ports.inbound;

import com.sw.av.application.dto.MemberDto;
import java.util.List;
import org.springframework.data.domain.Page;


public interface MemberServicePort {
    
    Page<MemberDto> findBy(String text, List<Long> updateDateList , int page, int size, String[] sort) throws Exception;
    
    Page<MemberDto> findBy(Long dateFrom, Long dateTo , int page, int size, String[] sort) throws Exception;
    
    MemberDto findById(Long id) throws Exception;
    
    MemberDto save(MemberDto employeeDto, Long idUser) throws Exception;
    
    MemberDto merge(Long id, MemberDto employeeDto, Long idUser) throws Exception;
}
