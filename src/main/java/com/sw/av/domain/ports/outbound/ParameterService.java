package com.sw.av.domain.ports.outbound;


import com.sw.av.domain.entities.Parameter;
import java.util.Optional;

public interface ParameterService extends ModelService<Parameter, Long> {
    
    Optional<Parameter> findByCod(String cod);
    
}
