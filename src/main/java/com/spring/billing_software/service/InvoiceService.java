package com.spring.billing_software.service;

import com.spring.billing_software.dto.InvoiceRequestDTO;
import com.spring.billing_software.entity.*;
import com.spring.billing_software.exception.InsufficientStockException;
import com.spring.billing_software.exception.ResourceNotFoundException;
import com.spring.billing_software.repository.CustomerRepository;
import com.spring.billing_software.repository.InvoiceRepository;
import com.spring.billing_software.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Invoice createInvoice(InvoiceRequestDTO dto) {

        Customer customer = customerRepository.findById(dto.getCustomerId());
        if (customer == null) {
            throw new ResourceNotFoundException("Customer not found");
        }

        double totalAmount = 0;
        double totalTax = 0;
        List<InvoiceItem> items = new ArrayList<>();

        for (InvoiceRequestDTO.InvoiceItemRequest req : dto.getItems()) {

            Product product = productRepository.findById(req.getProductId());
            if (product == null) {
                throw new ResourceNotFoundException("Product not found");
            }

            if (product.getStockQuantity() < req.getQuantity()) {
                throw new InsufficientStockException("Insufficient stock");
            }

            double itemTotal = product.getPrice() * req.getQuantity();
            double tax = itemTotal * product.getGstPercentage() / 100;

            product.setStockQuantity(product.getStockQuantity() - req.getQuantity());
            productRepository.updateById(product.getProductId(), product);

            InvoiceItem item = new InvoiceItem();
            item.setProduct(product);
            item.setQuantity(req.getQuantity());
            item.setPrice(product.getPrice());
            item.setTaxAmount(tax);
            item.setDiscount(0);
            item.setTotal(itemTotal + tax);

            items.add(item);
            totalAmount += itemTotal;
            totalTax += tax;
        }

        Invoice invoice = new Invoice();
        invoice.setCustomer(customer);
        invoice.setItems(items);
        invoice.setTotalAmount(totalAmount);
        invoice.setTotalTax(totalTax);
        invoice.setDiscount(dto.getDiscount());
        invoice.setFinalAmount(totalAmount + totalTax - dto.getDiscount());

        long invoiceId = invoiceRepository.saveInvoice(invoice);
        invoice.setInvoiceId(invoiceId);

        invoiceRepository.saveInvoiceItems(invoiceId, items);

        return invoice;
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Invoice getInvoiceById(long id) {
        Invoice invoice = invoiceRepository.findById(id);
        if (invoice == null) {
            throw new ResourceNotFoundException("Invoice not found");
        }
        return invoice;
    }

    public List<Invoice> getInvoicesByCustomer(int customerId) {
        return invoiceRepository.findByCustomerId(customerId);
    }

    public void deleteInvoice(long id) {
        invoiceRepository.deleteById(id);
    }
}
