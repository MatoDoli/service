package com.peli.service;

import org.springframework.web.bind.annotation.*;
import java.util.Optional;


@RestController
public class ServiceController extends CustomerServiceClient {
    private final CustomerRepository customerRepository;

    public ServiceController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostMapping("/add")
    public String addCustomer(Integer userId, String title, String body) {
        Customer customer = new Customer();
        customer.setUserId(userId);
        customer.setTitle(title);
        customer.setBody(body);

        if (existsCustomerByUserId(userId)) {
            customerRepository.save(customer);
            return "Added new customer";
        } else {
            throw new RuntimeException("UserId is not in external API");
        }
    }

    @GetMapping("/get")
    public Iterable<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping("/find/{id}")
    public Optional<Customer> findCustomerById(Integer id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            return customer;
        } else if (existsCustomerById(id)) {
            Customer existingCustomer = getCustomerById(id);
            updateCustomer(existingCustomer, id, existingCustomer.getUserId(),
                    existingCustomer.getTitle(), existingCustomer.getBody());
            customerRepository.save(existingCustomer);
            return Optional.of(existingCustomer);
        }
        throw new RuntimeException("Customer not found");
    }

    @PutMapping("/update/{id}")
    public Optional<Customer> updateCustomer(Integer id, Integer userId, String title, String body) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            Customer newCustomer = new Customer();
            updateCustomer(newCustomer, id, userId, title, body);
            customerRepository.save(newCustomer);
            return Optional.of(newCustomer);
        } else if (existsCustomerById(id)) {
            Customer existingCustomer = getCustomerById(id);
            updateCustomer(existingCustomer, id, userId, title, body);
            customerRepository.save(existingCustomer);
            return Optional.of(existingCustomer);
        }
        throw new RuntimeException("Customer not found");
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
        return "Customer deleted";
    }
}


