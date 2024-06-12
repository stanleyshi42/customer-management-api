package com.example.customer_management_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.customer_management_api.entity.Customer;
import com.example.customer_management_api.service.CustomerService;

@RestController
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    CustomerService service;

    @GetMapping("/customer")
    public List<Customer> findAllCustomers() {
        logger.info("GET request to findAllCustomers()");
        return service.findAllCustomers();
    }

    @GetMapping("/customer/firstname")
    public List<Customer> findAllCustomersByOrderByFirstName() {
        logger.info("GET request to findAllCustomersByOrderByFirstName()");
        return service.findAllCustomersByOrderByFirstName();
    }

    @GetMapping("/customer/lastname")
    public List<Customer> findAllCustomersByOrderByLastName() {
        logger.info("GET request to findAllCustomersByOrderByLastName()");
        return service.findAllCustomersByOrderByLastName();
    }

    @GetMapping("/customer/id/{id}")
    public Customer findCustomerById(@PathVariable long id) {
        logger.info("GET request to findCustomerById() with ID: {}", id);

        Customer result = service.findCustomerById(id);
        if (result == null) {
            logger.debug("Customer with ID: {} not found", id);
            return null;
        }

        return result;
    }

    @GetMapping("/customer/firstname/{firstName}")
    public List<Customer> findCustomersByFirstName(@PathVariable String firstName) {
        logger.info("GET request to findAllCustomersByUsername() with username: {}", firstName);
        return service.findCustomersByFirstName(firstName);
    }

    @PostMapping("/customer")
    public Customer addCustomer(@RequestBody Customer customer) {
        logger.info("POST request to addCustomer()");

        // Validate email syntax with regex
        Pattern emailPattern = Pattern.compile("^[^@]+@[^@]+\\.[^@]+$");
        Matcher matcher = emailPattern.matcher(customer.getEmail());
        if (!matcher.find()) {
            logger.debug("New customer registration failed: invalid email syntax");
            return null;
        }

        // Validate phone number syntax with regex
        Pattern phoneNumberPattern = Pattern.compile("^[0-9]{10}$");
        matcher = phoneNumberPattern.matcher(customer.getPhoneNumber());
        if (!matcher.find()) {
            logger.debug("New customer registration failed: invalid phone number syntax");
            return null;
        }

        // Prevent overwriting an existing customer
        customer.setId(0);

        // Check if email is already taken
        if (service.findCustomerByEmail(customer.getEmail()) != null) {
            logger.debug("New customer registration failed: email already taken");
            return null;
        }

        Customer result = service.addCustomer(customer);
        if (result != null) {
            logger.info("New customer successfully registered");
            return result;
        }

        logger.trace("New customer registration failed");
        return null;
    }

    @PutMapping("/customer")
    public Customer updateCustomer(@RequestBody Customer customer) {
        logger.info("PUT request to updateCustomer() with ID: {}", customer.getId());

        // Check if update target exists
        Customer result = service.findCustomerById(customer.getId());
        if (result == null) {
            logger.debug("Update failed: customer with ID: {} not found", customer.getId());
            return null;
        }

        logger.info("Customer with ID: {} updated", customer.getId());
        return service.updateCustomer(customer);
    }

    @DeleteMapping("/customer/id/{id}")
    public boolean deleteCustomer(@PathVariable long id) {
        logger.info("DELETE request to deleteCustomer() with ID: {}", id);

        int count = service.findAllCustomers().size();
        service.deleteCustomerById(id);

        if (count != service.findAllCustomers().size()) {
            logger.debug("Deleted customer with ID: {}", id);
            return true;
        }
        return false;
    }

}
