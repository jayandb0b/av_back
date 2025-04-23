package com.sw.av.domain.ports.outbound;


import com.sw.av.domain.entities.MemberPayment;
import com.sw.av.domain.exceptions.DAOException;
import java.util.List;

public interface MemberPaymentService extends ModelService<MemberPayment, Long> {
    
    List<MemberPayment> findBy(Long idMemeber) throws DAOException;
    
}
