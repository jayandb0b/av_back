package com.sw.av.domain.ports.outbound;


import com.sw.av.domain.entities.Member;
import com.sw.av.domain.exceptions.DAOException;
import java.util.Date;
import org.springframework.data.domain.Page;

public interface MemberService extends ModelService<Member, Long> {
    
    Page<Member> findBy(String text, Date dateFrom, Date dateTo ,int page, int size, String[] sort) throws DAOException;
    
    Page<Member> findBy(Date dateFrom, Date dateTo, int page, int size, String[] sort) throws DAOException;
    
}
