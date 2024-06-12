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
        Customer actualCustomer = controller.addCustomer(newCustomer);
        assertThat(actualCustomer).isNotNull();

        // Test read
        assertEquals("Test", actualCustomer.getFirstName());

        // Test update
        String expectedEmail = "myemail@gmail.com";
        String expectedFirstName = "John";
        actualCustomer.setEmail(expectedEmail);
        actualCustomer.setFirstName(expectedFirstName);
        actualCustomer = controller.updateCustomer(actualCustomer);
        assertEquals(expectedEmail, actualCustomer.getEmail());
        assertEquals(expectedFirstName, actualCustomer.getFirstName());

        // Test delete
        // If this test fails, test data will remain in database
        assertTrue(controller.deleteCustomer(actualCustomer.getId()));

    }

    @Test
    void regexTest() {

        // Test email regex
        Customer newCustomer = new Customer(0, "Test", "Name", "test.com", "1112223333");
        Customer actualCustomer = controller.addCustomer(newCustomer);
        assertNull(actualCustomer);

        // Test phone number regex
        newCustomer = new Customer(0, "Test", "Name", "test@email.com", "123");
        actualCustomer = controller.addCustomer(newCustomer);
        assertNull(actualCustomer);
    }

}
