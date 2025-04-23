package com.sw.av.application.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberPaymentDto {
    
    @Setter(onMethod = @__(@JsonSetter(value = "id"))) 
    @Getter(onMethod = @__(@JsonGetter(value = "id")))
    private Long id;
    
    @Setter(onMethod = @__(@JsonSetter(value = "memberId"))) 
    @Getter(onMethod = @__(@JsonGetter(value = "memberId")))
    private Long memberId;
    
    @Setter(onMethod = @__(@JsonSetter(value = "printedCarnet"))) 
    @Getter(onMethod = @__(@JsonGetter(value = "printedCarnet")))
    private String printedCarnet;
    
    @Setter(onMethod = @__(@JsonSetter(value = "amount"))) 
    @Getter(onMethod = @__(@JsonGetter(value = "amount")))
    private BigDecimal amount;
        
    @Setter(onMethod = @__(@JsonSetter(value = "comments"))) 
    @Getter(onMethod = @__(@JsonGetter(value = "comments")))
    private String comments;
    
    @Setter(onMethod = @__(@JsonSetter(value = "paymentDate"))) 
    @Getter(onMethod = @__(@JsonGetter(value = "paymentDate")))
    private Long paymentDate;
    
    @Setter(onMethod = @__(@JsonSetter(value = "active"))) 
    @Getter(onMethod = @__(@JsonGetter(value = "active")))
    private String  active;
    
    @Setter(onMethod = @__(@JsonSetter(value = "dateUpdated"))) 
    @Getter(onMethod = @__(@JsonGetter(value = "dateUpdated")))
    private Long dateUpdated;
    
}

    