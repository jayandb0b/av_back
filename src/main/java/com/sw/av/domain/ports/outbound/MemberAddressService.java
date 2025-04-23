package com.sw.av.domain.ports.outbound;


import com.sw.av.domain.entities.MemberAddress;
import com.sw.av.domain.exceptions.DAOException;
import java.util.List;

public interface MemberAddressService extends ModelService<MemberAddress, Long> {
    
    List<MemberAddress> findBy(Long idMemeber) throws DAOException;
    
}
