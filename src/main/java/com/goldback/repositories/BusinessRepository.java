package com.goldback.repositories;

import com.goldback.modals.Business;
import org.springframework.data.domain.Range;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BusinessRepository extends JpaRepository<Business, UUID> {
    Optional<Business> findByNumber(String contact);

    @Query("SELECT COUNT(b) FROM Business b")
    Integer countOfBusinesses();


    Optional<Business> findByName(String name);

    @Query("""
            SELECT c FROM Business c
            WHERE lower(c.name) LIKE lower(concat('%', :keyword, '%'))
            """)
    List<Business> searchBusiness(String keyword);


}
