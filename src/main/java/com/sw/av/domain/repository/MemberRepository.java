package com.sw.av.domain.repository;

import com.sw.av.domain.entities.Member;
import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


public interface MemberRepository extends JpaRepository<Member, Long>, 
                                          JpaSpecificationExecutor<Member>, 
                                          PagingAndSortingRepository<Member, Long>{
    
    
    @Query("FROM Member u "
         + " WHERE 1=1 "
         + " AND (:text IS NULL OR UPPER(u.surnameName) LIKE UPPER(CONCAT('%', :text, '%'))) "
         + " OR (:text IS NULL OR UPPER(u.dni) LIKE UPPER(CONCAT('%', :text, '%'))) "
         + " OR (:text IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :text, '%'))) "
         + " AND ((:dateFrom IS NULL AND :dateTo IS NULL) " 
         +  "   OR (:dateFrom IS NOT NULL AND :dateTo IS NOT NULL AND u.dateUpdated >= :dateFrom AND u.dateUpdated <= :dateTo) " 
         +  "   OR (:dateFrom IS NOT NULL AND :dateTo IS NULL AND u.dateUpdated >= :dateFrom) " 
         +  "   OR (:dateFrom IS NULL AND :dateTo IS NOT NULL AND u.dateUpdated <= :dateTo))"
         )
    Page<Member> findBy(
                                        @Param("text") String text, 
                                        @Param("dateFrom") Date dateFrom, 
                                        @Param("dateTo") Date dateTo, 
                                        Pageable pageable
                                       );
    
     @Query("FROM Member u JOIN MemberPayment mp "
                + " ON u.id = mp.memberId "
                + " WHERE 1=1 "
                + " AND mp.paymentDate >= :dateFrom AND mp.paymentDate <= :dateTo AND mp.active='Y' "
                 + "AND u.active='Y' "
         )
    Page<Member> findBy(
                                        @Param("dateFrom") Date dateFrom, 
                                        @Param("dateTo") Date dateTo, 
                                        Pageable pageable
                                       );
}
