package com.emanuelpontes.crmtest.api.customer.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.emanuelpontes.crmtest.api.customer.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT c FROM Customer c WHERE (c.updatedAt IS NULL OR c.updatedAt > :date)")
    List<Customer> findByUpdatedAtAfter(@Param("date") LocalDateTime lastSyncTime);
    
}
