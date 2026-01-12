package com.spring.billing_software.service;
import com.spring.billing_software.entity.Customer;
//import com.spring.billing_software.exception.ResourceNotFoundException;
import com.spring.billing_software.repository.CustomerRepository;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }


    public List<Customer> getAllCustomer() {
        return repository.findAll();
    }

    public Customer createCustomer(Customer Customer) {
        try
        {
            repository.save(Customer);

        }
        catch (Exception e) {
            System.out.println(e);
            throw e;
        }

        return Customer;
    }

    public Customer findByid(int id) {
        try {
            return repository.findById(id);
        } catch (Exception e) {
            throw e;
        }
    }


//    public boolean deleteById(int id) {
//        try {
//            repository.deleteById(id);
//            return true;
//        } catch (Exception e) {
//            throw e;
//
//        }
//
//    }


//    public Customer updateCustomer(int id, Customer customer) {
//        try
//        {
//            repository.updateById(id, customer);
//
//        }
//        catch (ResourceNotFoundException e)
//        {
//            throw e;
//        }
//        return customer;
//    }


    public List<Customer> searchByName(String name) {
        try {
            return repository.searchByName(name);
        } catch (Exception e) {
            System.out.println(e);
            return List.of();
        }
    }

//    public List<Customer> filterByPrice(int min, int max) {
//        return repository.findByPriceBetween(min, max);
//    }
}