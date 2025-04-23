package com.sw.av.infrastructure.persistence;

import com.sw.av.domain.entities.MemberPhone;
import com.sw.av.domain.exceptions.DAOException;
import com.sw.av.domain.ports.outbound.MemberPhoneService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sw.av.domain.repository.MemberPhoneRepository;


//extends GsModelServiceImpl<ParamAplicacion,String, ParamApplicationDao>
@Service
//@EnableJpaRepositories({"com.gs.commons.model.dao"})
//@Import({ParamApplication.class})
@Slf4j
public class MemberPhoneServiceImpl extends ModelServiceImpl<MemberPhone, Long, MemberPhoneRepository> implements MemberPhoneService {

    @Autowired
    private MemberPhoneRepository memberPhoneRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MemberPhone> findBy(Long idMemeber) throws DAOException {
        
        List<MemberPhone> memberPhoneList;
        
        try
        {
            
            memberPhoneList = memberPhoneRepository.findBy(idMemeber);
           
        }
        catch(Exception e){
            log.error("ERROR IN MemberPhoneSreviceImpl.findBy("+idMemeber+")", e);
            throw new DAOException(e);
        }
        
        return memberPhoneList;   
        
    }

}
