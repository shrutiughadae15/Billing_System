package com.spring.billing_software.entity;

public class InvoiceItem {

    private Long id;
    private String product;
    private int quantity;
    private double price;
    private double taxAmount;
    private double total;

    // Default Constructor
    public InvoiceItem() {}

    // Parameterized Constructor
    public InvoiceItem(Long id, String product, int quantity, double price, double taxAmount) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.taxAmount = taxAmount;
        calculateTotal();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        calculateTotal();
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
        calculateTotal();
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
        calculateTotal();
    }

    public double getTotal() {
        return total;
    }

    // Business Logic
    private void calculateTotal() {
        this.total = (price * quantity) + taxAmount;
    }
}
