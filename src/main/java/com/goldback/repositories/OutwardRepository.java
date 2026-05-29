package com.goldback.repositories;

import com.goldback.modals.Outward;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OutwardRepository extends JpaRepository<Outward, UUID> {

    Page<Outward> findByBusinessId(UUID businessId, Pageable pageable);

    Optional<Outward> findByInwardId(UUID inwardId);

    @Query("SELECT SUM(o.moneyTaken) FROM Outward o")
    Double sumMoneyGivenByBusiness();

    @Query("SELECT SUM(o.interest) FROM Outward o WHERE o.status = 'GIVEN'")
    Double sumByInterest();

    @Query("SELECT SUM(o.moneyTaken) FROM Outward o WHERE o.status = 'GIVEN'")
    Double sumOfNotCollected();

    @Query("SELECT AVG(o.interest) FROM Outward o")
    Double averageInterestGiven();
}
