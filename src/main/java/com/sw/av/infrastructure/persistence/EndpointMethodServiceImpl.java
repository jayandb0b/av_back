package com.sw.av.infrastructure.persistence;


import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.sw.av.domain.repository.EndpointMethodRepository;
import com.sw.av.domain.entities.EndpointMethod;
import com.sw.av.domain.ports.outbound.EndpointMethodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
public class EndpointMethodServiceImpl extends ModelServiceImpl<EndpointMethod,Long, EndpointMethodRepository> implements EndpointMethodService {
        
    @Autowired
    private EndpointMethodRepository endpointMethodRepository;
    
    @Override
    @Transactional(readOnly = true)
    public EndpointMethod getEndpointMethod(String urlMethod, String app_name) {
        
        EndpointMethod endpointMethodReturn = null;
        Pattern pattern = null;
        try {
            if (urlMethod != null && app_name != null) {

                List<EndpointMethod> endpointMethods = endpointMethodRepository.getEndpointMethodList(app_name);
                
                for(EndpointMethod endpointMethod : endpointMethods){
                    
                    if(endpointMethod.getNameMethod().equals(urlMethod)){
                        endpointMethodReturn = endpointMethod;
                        break;
                    }
                    else{
                        pattern = Pattern.compile(endpointMethod.getNameMethod());
                        
                        Matcher matcher = pattern.matcher(urlMethod);
                        
                        if(matcher.find()){
                            endpointMethodReturn = endpointMethod;
                        }
                        
                    }
                    
                }

                
                
                return endpointMethodReturn;
            }
        }
        catch(Exception e){
            log.error(e.toString());
        }
            
        return null;
    }
    
}
