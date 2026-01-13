package com.spring.billing_software.repository;

import com.spring.billing_software.entity.Product;
import com.spring.billing_software.exception.ResourceNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Product> rowMapper = (rs, rowNum) -> {
        Product p = new Product();
        p.setProductId(rs.getInt("product_id"));
        p.setName(rs.getString("name"));
        p.setPrice(rs.getDouble("price"));
        p.setStockQuantity(rs.getInt("stock_quantity"));
        p.setGstPercentage(rs.getDouble("gst_percentage"));
        return p;
    };

    public List<Product> findAll() {
        return jdbcTemplate.query("SELECT * FROM product", rowMapper);
    }

    public Product findById(int id) {
        List<Product> list = jdbcTemplate.query(
                "SELECT * FROM product WHERE product_id = ?",
                rowMapper,
                id
        );
        return list.isEmpty() ? null : list.get(0);
    }

    public void save(Product product) {
        jdbcTemplate.update(
                "INSERT INTO product (name, price, stock_quantity, gst_percentage) VALUES (?, ?, ?, ?, ?)",
                product.getProductId(),
                product.getName(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getGstPercentage()
        );
    }

    public void deleteById(int id) {
        int rows = jdbcTemplate.update("DELETE FROM product WHERE product_id = ?", id);
        if (rows == 0) {
            throw new ResourceNotFoundException("Product not found");
        }
    }

    public void updateById(int id, Product product) {
        int rows = jdbcTemplate.update(
                "UPDATE product SET name = ?, price = ?, stock_quantity = ?, gst_percentage = ? WHERE product_id = ?",
                product.getName(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getGstPercentage(),
                id
        );
        if (rows == 0) {
            throw new ResourceNotFoundException("Product not found");
        }
    }
}
