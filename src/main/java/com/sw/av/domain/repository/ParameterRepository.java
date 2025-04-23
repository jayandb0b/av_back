package com.sw.av.domain.repository;

import com.sw.av.domain.entities.Parameter;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


public interface ParameterRepository extends JpaRepository<Parameter, Long>,
                                      JpaSpecificationExecutor<Parameter>,
                                      PagingAndSortingRepository<Parameter, Long>{

    @Query("FROM Parameter u "
         + "WHERE u.cod = :cod "
          )
    Optional<Parameter> getByCod(@Param("cod") String cod);
}
