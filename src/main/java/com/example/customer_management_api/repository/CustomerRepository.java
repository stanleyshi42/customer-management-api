package com.example.customer_management_api.repository;

import com.example.customer_management_api.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findCustomerByEmail(String email);
    List<Customer> findCustomersByFirstName(String username);
}
