package com.example.customer_management_api.service;

import com.example.customer_management_api.entity.Customer;
import com.example.customer_management_api.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository repo;

    public List<Customer> findAllCustomers() {
        return repo.findAll();
    }

    public Customer findCustomerById(long id) {
        return repo.findById(id).orElse(null);
    }

    public Customer findCustomerByEmail(String email) {
        return repo.findCustomerByEmail(email).orElse(null);
    }

    public List<Customer> findCustomersByFirstName(String firstName) {
        return repo.findCustomersByFirstName(firstName);
    }

    public Customer addCustomer(Customer customer) {
        return repo.save(customer);
    }

    public Customer updateCustomer(Customer customer) {
        return repo.save(customer);
    }

    public void deleteCustomerById(long id) {
        repo.deleteById(id);
    }
}
