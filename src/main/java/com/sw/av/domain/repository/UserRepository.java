package com.sw.av.domain.repository;

import com.sw.av.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<User, Long>,
                                 JpaSpecificationExecutor<User>,
                                 PagingAndSortingRepository<User, Long>{
    
    @Query("FROM User u "
         + "WHERE u.username = :username "
          )
    User getByUsername(@Param("username") String username);
}
