package com.sw.av.domain.repository;

import com.sw.av.domain.entities.MemberAddress;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


public interface MemberAddressRepository extends JpaRepository<MemberAddress, Long>, 
                                          JpaSpecificationExecutor<MemberAddress>, 
                                          PagingAndSortingRepository<MemberAddress, Long>{
    
    
    @Query("FROM MemberAddress u "
         + " WHERE 1=1 "
         + " AND (:idMember IS NULL OR u.memberId = :idMember) "
         )
    List<MemberAddress> findBy(
                                @Param("idMember") Long idMember
                            );
}
