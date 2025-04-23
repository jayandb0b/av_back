package com.sw.av.domain.entities;


import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PARAMETERS")
@Cacheable(false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parameter implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "COD")
    private String cod;
    
    @Column(name = "VALUE")
    private String value;

    @Column(name = "DESCRIPTION")
    private String desc;
    
        
}
