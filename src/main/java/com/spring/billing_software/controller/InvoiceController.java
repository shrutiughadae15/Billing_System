package com.spring.billing_software.controller;

import com.spring.billing_software.dto.InvoiceRequestDTO;
import com.spring.billing_software.entity.Invoice;
import com.spring.billing_software.entity.Product;
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

    @PostMapping
    public Invoice createInvoice(@RequestBody InvoiceRequestDTO dto) {
        return invoiceService.createInvoice(dto);
    }

    @GetMapping
    public ResponseEntity<List<Invoice>> getAll() {
        try {
            return new ResponseEntity<>(invoiceService.getAllInvoices(), HttpStatus.OK);
        }                                // Get the List as response
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("/{id}")
    public Invoice getInvoiceById(@PathVariable long id) {
        return invoiceService.getInvoiceById(id);
    }

    @GetMapping("/customer/{customerId}")
    public List<Invoice> getInvoicesByCustomer(@PathVariable int customerId) {
        return invoiceService.getInvoicesByCustomer(customerId);
    }

    @DeleteMapping("/{id}")
    public void deleteInvoice(@PathVariable long id) {
        invoiceService.deleteInvoice(id);
    }
}
