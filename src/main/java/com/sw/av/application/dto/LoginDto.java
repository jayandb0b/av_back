package com.sw.av.application.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class LoginDto {

    @Setter(onMethod = @__(@JsonSetter(value = "AuthToken"))) 
    @Getter(onMethod = @__(@JsonGetter(value = "AuthToken")))
    private String authTokebn;
    
    @Setter(onMethod = @__(@JsonSetter(value = "Type"))) 
    @Getter(onMethod = @__(@JsonGetter(value = "Type")))
    private String type = "Bearer";
    
    @Setter(onMethod = @__(@JsonSetter(value = "RefreshToken"))) 
    @Getter(onMethod = @__(@JsonGetter(value = "RefreshToken")))
    private String refreshToken;
    
}
