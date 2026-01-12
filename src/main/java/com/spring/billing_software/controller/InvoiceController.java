package com.spring.billing_software.controller;

import com.spring.billing_software.dto.InvoiceRequestDTO;
import com.spring.billing_software.entity.Invoice;
import com.spring.billing_software.service.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    // -----------------------------
    // POST /invoices
    // -----------------------------
    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@RequestBody InvoiceRequestDTO dto) {
        try {
            Invoice invoice = invoiceService.createInvoice(dto);
            return new ResponseEntity<>(invoice, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // -----------------------------
    // GET /invoices
    // -----------------------------
    @GetMapping
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        List<Invoice> invoices = invoiceService.getAllInvoices();
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    // -----------------------------
    // GET /invoices/{id}
    // -----------------------------
    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id) {
        try {
            Invoice invoice = invoiceService.getInvoiceById(id);
            return new ResponseEntity<>(invoice, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // -----------------------------
    // GET /invoices/customer/{customerId}
    // -----------------------------
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Invoice>> getInvoicesByCustomer(@PathVariable int customerId) {
        try {
            List<Invoice> invoices = invoiceService.getInvoicesByCustomer(customerId);
            return new ResponseEntity<>(invoices, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
