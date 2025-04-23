package com.sw.av.application.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EndpointMethodDto {
    
    @Setter(onMethod = @__(@JsonSetter(value = "id"))) 
    @Getter(onMethod = @__(@JsonGetter(value = "id")))
    private Long id;

    @Setter(onMethod = @__(@JsonSetter(value = "serviceName"))) 
    @Getter(onMethod = @__(@JsonGetter(value = "serviceName")))
    private String serviceName;

    @Setter(onMethod = @__(@JsonSetter(value = "nameMethod"))) 
    @Getter(onMethod = @__(@JsonGetter(value = "nameMethod")))
    private String nameMethod;
    
}
