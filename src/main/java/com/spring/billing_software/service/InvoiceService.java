package com.spring.billing_software.service;

import com.spring.billing_software.dto.InvoiceRequestDTO;
import com.spring.billing_software.entity.Customer;
import com.spring.billing_software.entity.Invoice;
import com.spring.billing_software.entity.InvoiceItem;
import com.spring.billing_software.entity.Product;
import com.spring.billing_software.exception.InsufficientStockException;
import com.spring.billing_software.exception.ResourceNotFoundException;
import com.spring.billing_software.repository.CustomerRepository;
import com.spring.billing_software.repository.InvoiceRepository;
import com.spring.billing_software.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public InvoiceService(InvoiceRepository invoiceRepository,
                          CustomerRepository customerRepository,
                          ProductRepository productRepository) {
        this.invoiceRepository = invoiceRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    // -----------------------------
    // POST /invoices
    // -----------------------------
    public Invoice createInvoice(InvoiceRequestDTO dto) {

        // Validate customer exists
        Customer customer = customerRepository.findById(dto.getCustomerId());
        if (customer == null) {
            throw new ResourceNotFoundException("Customer not found");
        }

        if (dto.getItems() == null || dto.getItems().isEmpty()) {
            throw new RuntimeException("Invoice must have at least one item");
        }

        List<InvoiceItem> invoiceItems = new ArrayList<>();
        double totalAmount = 0;
        double totalTax = 0;

        // Loop through items in request
        for (InvoiceRequestDTO.InvoiceItemRequest itemDTO : dto.getItems()) {

            Product product = productRepository.findById(itemDTO.getProductId());
            if (product == null) {
                throw new ResourceNotFoundException("Product not found: ID " + itemDTO.getProductId());
            }

            if (product.getStockQuantity() < itemDTO.getQuantity()) {
                throw new InsufficientStockException("Insufficient stock for product: " + product.getName());
            }

            // Calculate price and tax
            double price = product.getPrice();
            double itemTotal = price * itemDTO.getQuantity();
            double tax = itemTotal * product.getGstPercentage() / 100;

            // Reduce stock
            product.setStockQuantity(product.getStockQuantity() - itemDTO.getQuantity());
            productRepository.updateById(product.getProductId(), product);

            // Create invoice item
            InvoiceItem invoiceItem = new InvoiceItem();
            invoiceItem.setProduct(product);
            invoiceItem.setQuantity(itemDTO.getQuantity());
            invoiceItem.setPrice(price);
            invoiceItem.setTaxAmount(tax);
            invoiceItem.setDiscount(0); // optional
            invoiceItem.setTotal(itemTotal + tax);

            totalAmount += itemTotal;
            totalTax += tax;

            invoiceItems.add(invoiceItem);
        }

        // Create invoice
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(generateInvoiceId());
        invoice.setCustomer(customer);
        invoice.setItems(invoiceItems);
        invoice.setTotalAmount(totalAmount);
        invoice.setTotalTax(totalTax);
        invoice.setDiscount(dto.getDiscount());
        invoice.setFinalAmount(totalAmount + totalTax - dto.getDiscount());

        // Save in repository
        return invoiceRepository.createInvoice(invoice);
    }

    // -----------------------------
    // GET /invoices
    // -----------------------------
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.getAllInvoices();
    }

    // -----------------------------
    // GET /invoices/{id}
    // -----------------------------
    public Invoice getInvoiceById(Long id) {
        Invoice invoice = invoiceRepository.getInvoiceById(id);
        if (invoice == null) {
            throw new ResourceNotFoundException("Invoice not found: ID " + id);
        }
        return invoice;
    }

    // -----------------------------
    // GET /invoices/customer/{customerId}
    // -----------------------------
    public List<Invoice> getInvoicesByCustomer(int customerId) {
        Customer customer = customerRepository.findById(customerId);
        if (customer == null) {
            throw new ResourceNotFoundException("Customer not found: ID " + customerId);
        }

        List<Invoice> allInvoices = invoiceRepository.getAllInvoices();
        List<Invoice> customerInvoices = new ArrayList<>();
        for (Invoice invoice : allInvoices) {
            if (invoice.getCustomer().getCustomerId() == customerId) {
                customerInvoices.add(invoice);
            }
        }
        return customerInvoices;
    }

    // -----------------------------
    // Auto-generate invoice ID
    // -----------------------------
    private long generateInvoiceId() {
        return invoiceRepository.getAllInvoices().size() + 1L;
    }
}
