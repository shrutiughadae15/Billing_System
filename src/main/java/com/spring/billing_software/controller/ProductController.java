package com.spring.billing_software.controller;


import com.spring.billing_software.exception.ResourceNotFoundException;
import com.spring.billing_software.entity.Product;
import com.spring.billing_software.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService productService){
        this.service=productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        try {
            return new ResponseEntity<>(service.getAllProduct(), HttpStatus.OK);
        }                                // Get the List as response
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {


        return new ResponseEntity <> (service.createProduct(product),HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Product>  getById(@PathVariable int id){
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
    public ResponseEntity<Product> updateById(@PathVariable int id,@RequestBody Product p){
        try{

            return ResponseEntity.ok(service.updateProduct(id,p));
        } catch (ResourceNotFoundException e) {

            return ResponseEntity.notFound().build();
        }

    }

//    @GetMapping("/search/{name}")
//    public List<Product> searchByName(@PathVariable String name) throws ResourceNotFoundException {
//        return service.searchByName(name);
//    }

    //function for filter the min and max price
//    @GetMapping("/filter")
//    public List<Product> filterProduct(@RequestParam int min,
//                                           @RequestParam int max) {
//        return service.filterByPrice(min, max);
//    }


//    @GetMapping("/{search}")
//    public List<Product> search(){
//        return service.getAllProduct();
//
//    }
}
