package com.sw.av.application.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberDto {
    
    @Setter(onMethod = @__(@JsonSetter(value = "id"))) 
    @Getter(onMethod = @__(@JsonGetter(value = "id")))
    private Long id;
    
    @Setter(onMethod = @__(@JsonSetter(value = "surnameName"))) 
    @Getter(onMethod = @__(@JsonGetter(value = "surnameName")))
    private String surnameName;
    
    @Setter(onMethod = @__(@JsonSetter(value = "comments"))) 
    @Getter(onMethod = @__(@JsonGetter(value = "comments")))
    private String comments;

    @Setter(onMethod = @__(@JsonSetter(value = "dni"))) 
    @Getter(onMethod = @__(@JsonGetter(value = "dni")))
    private String dni;
    
    @Setter(onMethod = @__(@JsonSetter(value = "email"))) 
    @Getter(onMethod = @__(@JsonGetter(value = "email")))
    private String email;
    
    @Setter(onMethod = @__(@JsonSetter(value = "memberNumber"))) 
    @Getter(onMethod = @__(@JsonGetter(value = "memberNumber")))
    private Long memberNumber;
    
    @Setter(onMethod = @__(@JsonSetter(value = "active"))) 
    @Getter(onMethod = @__(@JsonGetter(value = "active")))
    private String  active;
    
    @Setter(onMethod = @__(@JsonSetter(value = "dateUpdate"))) 
    @Getter(onMethod = @__(@JsonGetter(value = "dateUpdate")))
    private Long dateUpdate;
    
}

    