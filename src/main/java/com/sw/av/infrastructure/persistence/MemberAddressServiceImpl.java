package com.sw.av.infrastructure.persistence;

import com.sw.av.domain.entities.MemberAddress;
import com.sw.av.domain.exceptions.DAOException;
import com.sw.av.domain.ports.outbound.MemberAddressService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sw.av.domain.repository.MemberAddressRepository;


//extends GsModelServiceImpl<ParamAplicacion,String, ParamApplicationDao>
@Service
//@EnableJpaRepositories({"com.gs.commons.model.dao"})
//@Import({ParamApplication.class})
@Slf4j
public class MemberAddressServiceImpl extends ModelServiceImpl<MemberAddress, Long, MemberAddressRepository> implements MemberAddressService {

    @Autowired
    private MemberAddressRepository memberAddressRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MemberAddress> findBy(Long idMemeber) throws DAOException {
        
        List<MemberAddress> memberAddressList;
        
        try
        {
            
            memberAddressList = memberAddressRepository.findBy(idMemeber);
           
        }
        catch(Exception e){
            log.error("ERROR IN MemberAddressSreviceImpl.findBy("+idMemeber+")", e);
            throw new DAOException(e);
        }
        
        return memberAddressList;   
        
    }

}
