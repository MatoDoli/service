package com.peli.service;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerServiceClient {

    private final String apiUsersUrl = "https://jsonplaceholder.typicode.com/users";
    private final String apiPostsUrl = "https://jsonplaceholder.typicode.com/posts";

    //check if exists userId in external API
    public boolean existsCustomerByUserId(Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUsersUrl + "/" + id;
        try {
            restTemplate.getForObject(url, Customer.class);
            return true;
        } catch (HttpClientErrorException e) {
            return false;
        }
    }

    //check if exists id in external API
    public boolean existsCustomerById(Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = apiPostsUrl + "/" + id;
        try {
            restTemplate.getForObject(url, Customer.class);
            return true;
        } catch (HttpClientErrorException e) {
            return false;
        }
    }

    //return Customer from external API
    public Customer getCustomerById(Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = apiPostsUrl + "/" + id;
        return restTemplate.getForObject(url, Customer.class);
    }

    //return all existing userId from external API
    //not used
    public List<Integer> getAllCustomersId() {
        RestTemplate restTemplate = new RestTemplate();
        Customer[] customersId = restTemplate.getForObject(apiUsersUrl, Customer[].class);
        assert customersId != null;
        List<Customer> list = Arrays.asList(customersId);
        return list.stream().map(Customer::getId).collect(Collectors.toList());
    }
}
