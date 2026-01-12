package com.spring.billing_software.controller;
import com.spring.billing_software.entity.Customer;
import com.spring.billing_software.exception.ResourceNotFoundException;
import com.spring.billing_software.entity.Customer;
import com.spring.billing_software.service.CustomerService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Customer")
public class CustomerController {
    private final CustomerService service;

    public CustomerController(CustomerService customerService){
        this.service=customerService;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAll() {
        try {
            return new ResponseEntity<>(service.getAllCustomer(), HttpStatus.OK);
        }                                // Get the List as response
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {


        return new ResponseEntity <> (service.createCustomer(customer),HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Customer>  getById(@PathVariable int id){
        return new ResponseEntity<>(service.findByid(id),HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public String deleteById(@PathVariable int id){
        try
        {
            service.deleteById(id);
            return "deleted Successfully";
        }catch (ResourceNotFoundException e) {
            return "Fail to delete";

        }

    }
    @PutMapping("{id}")
    public ResponseEntity<Customer> updateById(@PathVariable int id,@RequestBody Customer c){
        try{

            return ResponseEntity.ok(service.updateCustomer(id,c));
        } catch (ResourceNotFoundException e) {

            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/search/{name}")
    public List<Customer> searchByName(@PathVariable String name) throws ResourceNotFoundException {
        return service.searchByName(name);
    }

    //function for filter the min and max price
//    @GetMapping("/filter")
//    public List<Customer> filterCustomer(@RequestParam int min,
//                                       @RequestParam int max) {
//        return service.filterByP(min, max);
//    }


    @GetMapping("/{search}")
    public List<Customer> search(){
        return service.getAllCustomer();

    }
}
