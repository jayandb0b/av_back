package com.sw.av.domain.repository;

import com.sw.av.domain.entities.MemberPayment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


public interface MemberPaymentRepository extends JpaRepository<MemberPayment, Long>, 
                                                 JpaSpecificationExecutor<MemberPayment>, 
                                                 PagingAndSortingRepository<MemberPayment, Long>{

    
    @Query("FROM MemberPayment u "
         + " WHERE 1=1 "
         + " AND (:idMember IS NULL OR u.memberId = :idMember) "
         + "ORDER BY u.paymentDate DESC"
         )
    List<MemberPayment> findBy(
                                @Param("idMember") Long idMember
                            );
}
