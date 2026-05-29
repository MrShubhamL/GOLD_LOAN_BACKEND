package com.goldback.repositories;

import com.goldback.modals.Inward;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface InwardRepository extends JpaRepository<Inward , UUID> {

    Page<Inward> getInwardsByCustomer_Id(UUID id , Pageable pageable);


    @Query("SELECT SUM(i.moneyGiven) FROM Inward i")
    Double sumMoneyGiven();

    @Query("SELECT SUM(i.moneyGiven) FROM Inward i where i.status = 'COLLECTED'")
    Double sumOfMoneyGivenByStatus();


    @Query("SELECT SUM(i.moneyGiven) FROM Inward i where i.status = 'PENDING'")
    Double sumOfNotCollectedMoney();

    List<Inward> findInwardByStatus(String status);

    List<Inward> findInwardByIsOwnedTrue();

    List<Inward> findInwardByIsOwnedFalse();

    List<Inward> findAllByReturnDateBeforeAndStatus(String returnDate, String status);
}
