package com.sw.av.infrastructure.persistence;

import com.sw.av.domain.entities.Parameter;
import com.sw.av.domain.ports.outbound.ParameterService;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sw.av.domain.repository.ParameterRepository;

//extends GsModelServiceImpl<ParamAplicacion,String, ParamApplicationDao>
@Service
//@EnableJpaRepositories({"com.gs.commons.model.dao"})
//@Import({ParamApplication.class})
@Slf4j
public class ParameterServiceImpl extends ModelServiceImpl<Parameter, Long, ParameterRepository> implements ParameterService {

    @Autowired
    private ParameterRepository parameterRepository;

    @Override
    public Optional<Parameter> findByCod(String cod) {
        if (cod != null) {

            try {
                Optional<Parameter> parameter = parameterRepository.getByCod(cod);
                return parameter;

            } catch (Exception e) {
                log.error("🔥 🔥 🚒 ERROR IN ParameterServiceImpl.findByCod(" + cod + ")", e);
            }
        }

        return null;
    }
     
}
