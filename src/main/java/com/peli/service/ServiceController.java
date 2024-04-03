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
    public String addCustomer(@RequestParam Integer userId, @RequestParam String title, @RequestParam String body) {
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

    @GetMapping("/find")
    public Optional<Customer> findCustomerById(@RequestParam("id") Integer id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            return customer;
        } else if (existsCustomerById(id)) {
            Customer existingCustomer = getCustomerById(id);
            existingCustomer.setUserId(existingCustomer.getUserId());
            existingCustomer.setTitle(existingCustomer.getTitle());
            existingCustomer.setBody(existingCustomer.getBody());
            customerRepository.save(existingCustomer);
            return Optional.of(existingCustomer);
        }
        throw new RuntimeException("Customer not found");
    }

    @PostMapping("/update")
    public Optional<Customer> updateCustomer(@RequestParam("id") Integer id, @RequestParam String title,
                                             @RequestParam String body) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            Customer newCustomer = new Customer();
            newCustomer.setId(id);
            newCustomer.setTitle(title);
            newCustomer.setBody(body);
            customerRepository.save(newCustomer);
            return Optional.of(newCustomer);
        } else if (existsCustomerById(id)) {
            Customer existingCustomer = getCustomerById(id);
            existingCustomer.setTitle(title);
            existingCustomer.setBody(body);
            customerRepository.save(existingCustomer);
            return Optional.of(existingCustomer);
        }
        throw new RuntimeException("Customer not found");
    }

    @PostMapping("/delete")
    public String deleteCustomer(@RequestParam("id") Integer id) {
        customerRepository.deleteById(id);
        return "Customer deleted";
    }
}


