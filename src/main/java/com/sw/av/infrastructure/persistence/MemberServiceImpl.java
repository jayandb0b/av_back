package com.sw.av.infrastructure.persistence;

import com.sw.av.domain.entities.Member;
import com.sw.av.domain.exceptions.DAOException;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sw.av.domain.ports.outbound.MemberService;
import com.sw.av.domain.repository.MemberRepository;


//extends GsModelServiceImpl<ParamAplicacion,String, ParamApplicationDao>
@Service
//@EnableJpaRepositories({"com.gs.commons.model.dao"})
//@Import({ParamApplication.class})
@Slf4j
public class MemberServiceImpl extends ModelServiceImpl<Member, Long, MemberRepository> implements MemberService {

    @Autowired
    private MemberRepository employeeRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Member> findBy(String text, Date dateFrom, Date dateTo, int page, int size, String[] sort) throws DAOException {
        
        Page<Member> pageEmployee;
        
        try
        {
            List<Sort.Order> orders = getOrderList(sort);
            
            Pageable pagingSort = null;
            
            if(orders!=null && !orders.isEmpty())
            {
                pagingSort = PageRequest.of(page, size, Sort.by(orders));
            }else
            {
                pagingSort = PageRequest.of(page, size);
            }
           
            pageEmployee = employeeRepository.findBy(text, dateTo, dateTo, pagingSort);
           
        }
        catch(Exception e){
            log.error("ERROR IN EmployeeServiceImpl.findBy("+text+","+dateFrom+","+dateTo+","+page+","+size+","+sort+")", e);
            throw new DAOException(e);
        }
        
        return pageEmployee;   
        
    }

    @Override
    public Page<Member> findBy(Date dateFrom, Date dateTo, int page, int size, String[] sort) throws DAOException {
        Page<Member> pageEmployee;
        
        try
        {
            List<Sort.Order> orders = getOrderList(sort);
            
            Pageable pagingSort = null;
            
            if(orders!=null && !orders.isEmpty())
            {
                pagingSort = PageRequest.of(page, size, Sort.by(orders));
            }else
            {
                pagingSort = PageRequest.of(page, size);
            }
           
            pageEmployee = employeeRepository.findBy( dateFrom, dateTo, pagingSort);
           
        }
        catch(Exception e){
            log.error("ERROR IN EmployeeServiceImpl.findBy("+dateFrom+","+dateTo+","+page+","+size+","+sort+")", e);
            throw new DAOException(e);
        }
        
        return pageEmployee;   
    }

    
}
