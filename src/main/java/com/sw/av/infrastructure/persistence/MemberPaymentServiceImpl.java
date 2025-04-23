package com.sw.av.infrastructure.persistence;

import com.sw.av.domain.entities.MemberPayment;
import com.sw.av.domain.exceptions.DAOException;
import com.sw.av.domain.ports.outbound.MemberPaymentService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sw.av.domain.repository.MemberPaymentRepository;


//extends GsModelServiceImpl<ParamAplicacion,String, ParamApplicationDao>
@Service
//@EnableJpaRepositories({"com.gs.commons.model.dao"})
//@Import({ParamApplication.class})
@Slf4j
public class MemberPaymentServiceImpl extends ModelServiceImpl<MemberPayment, Long, MemberPaymentRepository> implements MemberPaymentService {

    @Autowired
    private MemberPaymentRepository memberPaymentRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MemberPayment> findBy(Long idMemeber) throws DAOException {
        
        List<MemberPayment> memberPaymentList;
        
        try
        {
            
            memberPaymentList = memberPaymentRepository.findBy(idMemeber);
           
        }
        catch(Exception e){
            log.error("ERROR IN MemberPaymentSreviceImpl.findBy("+idMemeber+")", e);
            throw new DAOException(e);
        }
        
        return memberPaymentList;   
        
    }

}
