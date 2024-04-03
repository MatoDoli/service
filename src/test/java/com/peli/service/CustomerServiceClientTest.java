package com.peli.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerServiceClientTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private ServiceController serviceController;

    @Test
    public void testGetCustomerById() {
        Customer customer = new Customer();
        customer.setId(1);
        customer.setUserId(1);
        customer.setTitle("title");
        customer.setBody("body");
        when(customerRepository.findById(1)).thenReturn(java.util.Optional.of(customer));

        Customer returnedCustomer = serviceController.getCustomerById(1);
        assertEquals(customer.getId(), returnedCustomer.getId());
    }

    @Test
    public void testExistsCustomerByUserId() {
        Customer customer = new Customer();
        customer.setId(1);
        customer.setUserId(1);
        customer.setTitle("title");
        customer.setBody("body");
        when(customerRepository.findById(1)).thenReturn(java.util.Optional.of(customer));

        boolean existsCustomer = serviceController.existsCustomerByUserId(1);
        assertTrue(existsCustomer);
    }

    @Test
    public void testExistsCustomerById() {
        Customer customer = new Customer();
        customer.setId(1);
        customer.setUserId(1);
        customer.setTitle("title");
        customer.setBody("body");
        when(customerRepository.findById(1)).thenReturn(java.util.Optional.of(customer));

        boolean existsCustomer = serviceController.existsCustomerById(1);
        assertTrue(existsCustomer);
    }
}