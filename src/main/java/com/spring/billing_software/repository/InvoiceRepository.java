package com.spring.billing_software.repository;

import com.spring.billing_software.entity.Invoice;
import com.spring.billing_software.entity.InvoiceItem;
import com.spring.billing_software.dto.InvoiceRequestDTO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InvoiceRepository {

    private final List<Invoice> invoiceList = new ArrayList<>();

    public Invoice createInvoice(Invoice invoice) {
        invoiceList.add(invoice);
        return invoice;
    }

    public List<Invoice> getAllInvoices() {
        return invoiceList;
    }

    public Invoice getInvoiceById(Long id) {
        return invoiceList.stream()
                .filter(inv -> inv.getInvoiceId() == id)
                .findFirst()
                .orElse(null);
    }
}