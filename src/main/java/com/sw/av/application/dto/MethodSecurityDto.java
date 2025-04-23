package com.sw.av.application.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MethodSecurityDto {
    
    @Setter(onMethod = @__(@JsonSetter(value = "id"))) 
    @Getter(onMethod = @__(@JsonGetter(value = "id")))
    private Long id;

    @Setter(onMethod = @__(@JsonSetter(value = "endpointMethodDto"))) 
    @Getter(onMethod = @__(@JsonGetter(value = "endpointMethodDto")))
    private EndpointMethodDto endpointMethodDto;

    @Setter(onMethod = @__(@JsonSetter(value = "idUser"))) 
    @Getter(onMethod = @__(@JsonGetter(value = "idUser")))
    private Long idUser;
        
}
