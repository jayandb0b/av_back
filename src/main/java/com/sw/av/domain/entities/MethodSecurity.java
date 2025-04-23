package com.sw.av.domain.entities;

import java.io.Serializable;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "WS_METHOD_SECURITY")
@Cacheable(value = false)
public class MethodSecurity implements Serializable {
    @Id
    @Column(name = "ID_METHOD_SECURITY")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_METHOD", referencedColumnName = "ID_ENDPOINT_METHOD")
    private EndpointMethod endpointMethod;
    
    @Column(name = "ID_USER")
    private Long idUser;

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
