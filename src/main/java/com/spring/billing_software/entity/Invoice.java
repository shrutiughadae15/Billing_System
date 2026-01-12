package com.spring.billing_software.entity;

import java.time.LocalDate;

public class Invoice {

    private Long invoiceId;
    private LocalDate invoiceDate;
    private String customer;
    private double totalAmount;
    private double totalTax;
    private double discount;
    private double finalAmount;

    // Default Constructor
    public Invoice() {}

    // Parameterized Constructor
    public Invoice(Long invoiceId, LocalDate invoiceDate, String customer,
                   double totalAmount, double totalTax, double discount) {
        this.invoiceId = invoiceId;
        this.invoiceDate = invoiceDate;
        this.customer = customer;
        this.totalAmount = totalAmount;
        this.totalTax = totalTax;
        this.discount = discount;
        this.finalAmount = totalAmount + totalTax - discount;
    }

    // Getters and Setters
    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
        calculateFinalAmount();
    }

    public double getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(double totalTax) {
        this.totalTax = totalTax;
        calculateFinalAmount();
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
        calculateFinalAmount();
    }

    public double getFinalAmount() {
        return finalAmount;
    }

    // Business Logic
    private void calculateFinalAmount() {
        this.finalAmount = totalAmount + totalTax - discount;
    }
}
