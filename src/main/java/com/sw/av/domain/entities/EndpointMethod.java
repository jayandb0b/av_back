package com.sw.av.domain.entities;

import java.io.Serializable;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import lombok.Data;

@Data
@Entity
@Table(name = "WS_ENDPOINT_METHOD")
@Cacheable(value = false)
public class EndpointMethod implements Serializable {

    @Id
    @Column(name = "ID_ENDPOINT_METHOD")
    private Long id;

    @Column(name = "SERVICE_NAME")
    private String serviceName;

    @Column(name = "NAME_METHOD")
    private String nameMethod;

    @Column(name = "ACTIVE")
    private String active;
    
    @Column(name = "USER_CREATED")
    private Long userCreated;
    
    @Column(name = "DATE_CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    
    @Column(name = "USER_UPDATED")
    private Long userUpdated;
    
    @Column(name = "DATE_UPDATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpdated;
}
