package com.sw.av.domain.entities;


import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MEMBERS")
@Cacheable(false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "SURNAME_NAME")
    private String surnameName;
    
    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "DNI")
    private String dni;
    
    @Column(name = "EMAIL")
    private String email;
    
    @Column(name = "MEMBER_NUMBER")
    private Long memberNumber;
    
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
