package com.sw.av.domain.repository;


import java.util.List;

import com.sw.av.domain.entities.EndpointMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


public interface EndpointMethodRepository extends JpaRepository<EndpointMethod, Long>,
                                           JpaSpecificationExecutor<EndpointMethod>,
                                           PagingAndSortingRepository<EndpointMethod, Long>{

    //@Query("FROM EndpointMethod u WHERE u.nameMethod = :nameMethod and u.active = :active and u.serviceName = :serviceName")
    @Query("FROM EndpointMethod u WHERE u.nameMethod LIKE :nameMethod and u.serviceName = :serviceName")
    List<EndpointMethod> getEndpointMethod(@Param("nameMethod") String nameMethod,
                                           @Param("serviceName") String serviceName);
    @Query("FROM EndpointMethod u WHERE u.serviceName = :serviceName")
    List<EndpointMethod> getEndpointMethodList(@Param("serviceName") String serviceName);
    
    
}