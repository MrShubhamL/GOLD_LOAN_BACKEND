package com.goldback.repositories;

import com.goldback.modals.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, UUID>{


    @Query("SELECT SUM(b.amountTaken) FROM Borrow b")
    Double sumAmountTaken();


    @Query("SELECT SUM(b.interest) FROM Borrow b")
    Double sumInterest();


}
