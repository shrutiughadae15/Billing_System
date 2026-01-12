package com.spring.billing_software.service;

import com.spring.billing_software.exception.ResourceNotFoundException;
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

    public Product createProduct(Product Product) {
        try
        {
            repository.save(Product);

        }
        catch (Exception e) {
            System.out.println(e);
            throw e;
        }

        return Product;
    }

    public Product findByid(int id) {
        try {
            return repository.findById(id);
        } catch (Exception e) {
            throw e;
        }
    }


    public boolean deleteById(int id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw e;

        }

    }


    public Product updateProduct(int id, Product product) {
        try
        {
            repository.updateById(id, product);

        }
        catch (ResourceNotFoundException e)
        {
            throw e;
        }
        return product;
    }


    public List<Product> searchByName(String name) {
        try {
            return repository.searchByName(name);
        } catch (Exception e) {
            System.out.println(e);
            return List.of();
        }
    }

    public List<Product> filterByPrice(int min, int max) {
        return repository.findByPriceBetween(min, max);
    }
}