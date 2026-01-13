package com.spring.billing_software.controller;

import com.spring.billing_software.entity.Customer;
import com.spring.billing_software.entity.Product;
import com.spring.billing_software.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

//    @GetMapping
//    public List<Product> getAll() {
//        return service.getAllProduct();
//    }
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
        return new ResponseEntity<>(service.createProduct(product), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable int id) {
        Product p = service.findById(id);
        return p != null ? ResponseEntity.ok(p) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody Product product) {
        return ResponseEntity.ok(service.updateProduct(id, product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        service.deleteById(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}
