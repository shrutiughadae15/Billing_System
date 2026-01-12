package com.spring.billing_software.dto;

import java.util.List;

public class InvoiceRequestDTO {

    private int customerId;
    private double discount;
    private List<InvoiceItemRequest> items; // List of items in the invoice

    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public double getDiscount() {
        return discount;
    }
    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public List<InvoiceItemRequest> getItems() {
        return items;
    }
    public void setItems(List<InvoiceItemRequest> items) {
        this.items = items;
    }

    // Inner class for invoice item request
    public static class InvoiceItemRequest {
        private int productId;
        private int quantity;

        public int getProductId() { return productId; }
        public void setProductId(int productId) { this.productId = productId; }

        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
    }
}
