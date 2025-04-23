package com.sw.av.application.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberAddressDto {
    
    @Setter(onMethod = @__(@JsonSetter(value = "id"))) 
    @Getter(onMethod = @__(@JsonGetter(value = "id")))
    private Long id;
    
    @Setter(onMethod = @__(@JsonSetter(value = "memberId"))) 
    @Getter(onMethod = @__(@JsonGetter(value = "memberId")))
    private Long memberId;
    
    @Setter(onMethod = @__(@JsonSetter(value = "street"))) 
    @Getter(onMethod = @__(@JsonGetter(value = "street")))
    private String street;
    
    @Setter(onMethod = @__(@JsonSetter(value = "postalCode"))) 
    @Getter(onMethod = @__(@JsonGetter(value = "postalCode")))
    private String postalCode;

    @Setter(onMethod = @__(@JsonSetter(value = "locality"))) 
    @Getter(onMethod = @__(@JsonGetter(value = "locality")))
    private String locality;
    
    @Setter(onMethod = @__(@JsonSetter(value = "province"))) 
    @Getter(onMethod = @__(@JsonGetter(value = "province")))
    private String province;
    
    @Setter(onMethod = @__(@JsonSetter(value = "isDefault"))) 
    @Getter(onMethod = @__(@JsonGetter(value = "isDefault")))
    private String isDefault;
    
    @Setter(onMethod = @__(@JsonSetter(value = "active"))) 
    @Getter(onMethod = @__(@JsonGetter(value = "active")))
    private String  active;
    
    @Setter(onMethod = @__(@JsonSetter(value = "dateUpdated"))) 
    @Getter(onMethod = @__(@JsonGetter(value = "dateUpdated")))
    private Long dateUpdated;
    
}

    