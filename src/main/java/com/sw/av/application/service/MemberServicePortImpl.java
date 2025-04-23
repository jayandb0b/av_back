package com.sw.av.application.service;

import com.sw.av.Constants;
import com.sw.av.domain.entities.Member;
import com.sw.av.application.dto.MemberDto;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import com.sw.av.domain.ports.outbound.MemberService;
import com.sw.av.application.mapper.MemberMapper;
import com.sw.av.domain.ports.inbound.MemberServicePort;


@Service
@Slf4j
public class MemberServicePortImpl implements MemberServicePort{

    @Autowired
    private MemberService memberService;
    
    @Autowired
    private MemberMapper memberMapper;
    
    
    @Override
    public Page<MemberDto> findBy(String text, List<Long> updateDateList, int page, int size, String[] sort) throws Exception {
        
        Page<MemberDto> pageEmployeeDto = Page.empty();
        
        try {
            
            Date dateFrom = null;
            Date dateTo = null;
            
            if(updateDateList!=null){
                dateFrom = new Date(updateDateList.get(0));
                
                if(updateDateList.size()==2)
                    dateTo = new Date(updateDateList.get(1));
            }
            
            Page<Member> pageEmployee = memberService.findBy(text, dateFrom, dateTo, page, size, sort);
            pageEmployeeDto = pageEmployee.map(memberMapper::toDto);

        } catch (Exception ex) {
            log.error("ERROR IN MemberServicePortImpl.findBy(" + text + "," + updateDateList + "," + page + "," + size + "," + sort + ")", ex);
            throw new Exception(ex);
        }
        
        return pageEmployeeDto;
    }

    @Override
    public Page<MemberDto> findBy(Long dateFrom, Long dateTo, int page, int size, String[] sort) throws Exception {
                Page<MemberDto> pageEmployeeDto = Page.empty();
        
        try {
            
            Date dateF = null;
            Date dateT = null;
            
            dateF = new Date(dateFrom);
            dateT = new Date(dateTo);
                        
            Page<Member> pageEmployee = memberService.findBy(dateF, dateT, page, size, sort);
            pageEmployeeDto = pageEmployee.map(memberMapper::toDto);

        } catch (Exception ex) {
            log.error("ERROR IN MemberServicePortImpl.findBy(" + dateFrom + "," + dateTo + "," + page + "," + size + "," + sort + ")", ex);
            throw new Exception(ex);
        }
        
        return pageEmployeeDto;
    }
    
    
    
    @Override
    public MemberDto findById(Long id) {
        return memberMapper.toDto(memberService.getById(id));
    }

    @Override
    public MemberDto save(MemberDto memberDto, Long idUser) {
        Member member = memberMapper.toEntity(memberDto);
        member.setDateCreated(new Date());
        member.setDateUpdated(new Date());
        member.setUserCreated(idUser);
        member.setUserUpdated(idUser);
        member.setActive(Constants.ACTIVE_Y);
        
        return memberMapper.toDto(memberService.save(member));
    }

    @Override
    public MemberDto merge(Long id, MemberDto employeeDto, Long idUser) throws Exception {
        
            Member employee = memberService.getById(id);
        
        if(employee == null){
            employeeDto = save(employeeDto,idUser);
        }
        else{
            employee = memberMapper.toEntity(employeeDto);
            employee.setId(employee.getId());
            employee.setDateUpdated(new Date());
            employee.setUserUpdated(idUser);
            employee = memberService.save(employee);
            employeeDto = memberMapper.toDto(employee);
        }
        
        return  employeeDto;
    }
 
    
}
