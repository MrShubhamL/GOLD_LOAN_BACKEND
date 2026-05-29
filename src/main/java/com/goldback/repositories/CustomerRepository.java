package com.goldback.repositories;

import com.goldback.modals.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    Optional<Customer> findCustomerByContact(String contact);


    @Query("""
            SELECT c FROM Customer c
            WHERE lower(c.name) LIKE lower(concat('%', :keyword, '%'))
            """)
    List<Customer> searchCustomer(String keyword);

    @Query("SELECT COUNT(c) FROM Customer c")
    Integer countOfCustomers();
}
