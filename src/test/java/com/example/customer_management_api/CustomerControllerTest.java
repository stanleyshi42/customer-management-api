package com.example.customer_management_api;

import com.example.customer_management_api.controller.CustomerController;
import com.example.customer_management_api.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class CustomerControllerTest {

    @Autowired
    CustomerController controller;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    void controllerCRUDTest() {

        // Test create
        Customer newCustomer = new Customer(0, "Test", "Name", "test@email.com", "1112223333");
        Customer result = controller.addCustomer(newCustomer);
        assertThat(result).isNotNull();

        // Test read
        assertEquals("Test", result.getFirstName());

        // Test update
        result.setEmail("myemail@gmail.com");
        result.setFirstName("User");
        result = controller.updateCustomer(result);
        assertEquals("myemail@gmail.com", result.getEmail());
        assertEquals("User", result.getFirstName());

        // Test delete
        // If this test fails, test data will remain in database
        assertTrue(controller.deleteCustomer(result.getId()));

    }

    @Test
    void regexTest() {

        // Test email regex
        Customer newCustomer = new Customer(0, "Test", "Name", "test.com", "1112223333");
        Customer result = controller.addCustomer(newCustomer);
        assertNull(result);

        // Test phone number regex
        newCustomer = new Customer(0, "Test", "Name", "test@email.com", "123");
        result = controller.addCustomer(newCustomer);
        assertNull(result);
    }

}
