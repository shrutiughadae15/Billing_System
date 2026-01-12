package com.spring.billing_software.entity;

public class Product {

    private int productId;
    private String name;
    private double price;
    private double gstPercentage;
    private int stockQuantity;


    public Product() {}

    public Product(int productId, String name, double price,int stockQuantity,double gstPercentage) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stockQuantity=stockQuantity;
        this.gstPercentage=gstPercentage;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getGstPercentage() {
        return gstPercentage;
    }

    public void setGstPercentage(double gstPercentage) {
        this.gstPercentage = gstPercentage;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}

