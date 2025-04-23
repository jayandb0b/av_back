package com.sw.av.domain.ports.outbound;


import com.sw.av.domain.entities.MemberPhone;
import com.sw.av.domain.exceptions.DAOException;
import java.util.List;

public interface MemberPhoneService extends ModelService<MemberPhone, Long> {
    
    List<MemberPhone> findBy(Long idMemeber) throws DAOException;
    
}
