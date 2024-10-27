package com.emanuelpontes.crmtest.api.customer.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.emanuelpontes.crmtest.api.customer.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{
    
}
