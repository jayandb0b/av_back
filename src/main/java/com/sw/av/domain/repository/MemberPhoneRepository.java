package com.sw.av.domain.repository;

import com.sw.av.domain.entities.MemberPhone;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


public interface MemberPhoneRepository extends JpaRepository<MemberPhone, Long>, 
                                          JpaSpecificationExecutor<MemberPhone>, 
                                          PagingAndSortingRepository<MemberPhone, Long>{
    
    
    @Query("FROM MemberPhone u "
         + " WHERE 1=1 "
         + " AND (:idMember IS NULL OR u.memberId = :idMember ) "
         )
    List<MemberPhone> findBy(
                                @Param("idMember") Long idMember
                            );
}
