package com.spring.billing_software.service;

import com.spring.billing_software.entity.Product;
import com.spring.billing_software.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getAllProduct() {
        return repository.findAll();
    }

    public Product createProduct(Product product) {
        repository.save(product);
        return product;
    }

    public Product findById(int id) {
        return repository.findById(id);
    }

    public void deleteById(int id) {
        repository.deleteById(id);
    }

    public Product updateProduct(int id, Product product) {
        repository.updateById(id, product);
        return product;
    }
}
