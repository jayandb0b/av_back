package com.sw.av.domain.repository;


import java.util.List;

import com.sw.av.domain.entities.MethodSecurity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


public interface MethodSecurityRepository extends  JpaRepository<MethodSecurity, Long>,
                                            JpaSpecificationExecutor<MethodSecurity>,
                                            PagingAndSortingRepository<MethodSecurity, Long>{ 
    
    @Query("FROM MethodSecurity u "
         + " WHERE u.endpointMethod.id = :endpointMethod "
         + " AND ( u.idUser = :idUser OR u.idUser = 0) "
          )
    List<MethodSecurity> checkAccess(@Param("endpointMethod") Long  endpointMethod,
                                     @Param("idUser") Long idUser
                                    );
    

}
