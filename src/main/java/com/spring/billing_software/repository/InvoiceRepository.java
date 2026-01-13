package com.spring.billing_software.repository;

import com.spring.billing_software.entity.Customer;
import com.spring.billing_software.entity.Invoice;
import com.spring.billing_software.entity.InvoiceItem;
import com.spring.billing_software.exception.ResourceNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class InvoiceRepository {

    private final JdbcTemplate jdbcTemplate;

    public InvoiceRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /* ---------- ROW MAPPER ---------- */

    private final RowMapper<Invoice> rowMapper = (rs, rowNum) -> {
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(rs.getLong("invoice_id"));

        Customer customer = new Customer();
        customer.setCustomerId(rs.getInt("customer_id"));
        customer.setName(rs.getString("name"));
        customer.setPhone(rs.getString("phone"));

        invoice.setCustomer(customer);
        invoice.setTotalAmount(rs.getDouble("total_amount"));
        invoice.setTotalTax(rs.getDouble("total_tax"));
        invoice.setDiscount(rs.getDouble("discount"));
        invoice.setFinalAmount(rs.getDouble("final_amount"));

        return invoice;
    };

    /* ---------- SAVE ---------- */

    public long saveInvoice(Invoice invoice) {

        String sql = """
                INSERT INTO invoice (customer_id, total_amount, total_tax, discount, final_amount)
                VALUES (?, ?, ?, ?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, invoice.getCustomer().getCustomerId());
            ps.setDouble(2, invoice.getTotalAmount());
            ps.setDouble(3, invoice.getTotalTax());
            ps.setDouble(4, invoice.getDiscount());
            ps.setDouble(5, invoice.getFinalAmount());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public void saveInvoiceItems(long invoiceId, List<InvoiceItem> items) {
        String sql = """
                INSERT INTO invoice_item
                (invoice_id, product_id, quantity, price, tax_amount, discount, total)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        for (InvoiceItem item : items) {
            jdbcTemplate.update(
                    sql,
                    invoiceId,
                    item.getProduct().getProductId(),
                    item.getQuantity(),
                    item.getPrice(),
                    item.getTaxAmount(),
                    item.getDiscount(),
                    item.getTotal()
            );
        }
    }

    /* ---------- FETCH ---------- */

    public List<Invoice> findAll() {
        return jdbcTemplate.query(
                "SELECT i.*, c.name, c.phone FROM invoice i JOIN customer c ON i.customer_id=c.customer_id",
                rowMapper
        );
    }

    public Invoice findById(long id) {
        List<Invoice> list = jdbcTemplate.query(
                "SELECT i.*, c.name, c.phone FROM invoice i JOIN customer c ON i.customer_id=c.customer_id WHERE i.invoice_id=?",
                rowMapper,
                id
        );
        return list.isEmpty() ? null : list.get(0);
    }

    public List<Invoice> findByCustomerId(int customerId) {
        return jdbcTemplate.query(
                "SELECT i.*, c.name, c.phone FROM invoice i JOIN customer c ON i.customer_id=c.customer_id WHERE c.customer_id=?",
                rowMapper,
                customerId
        );
    }

    /* ---------- DELETE ---------- */

    public void deleteById(long id) {
        int rows = jdbcTemplate.update(
                "DELETE FROM invoice WHERE invoice_id=?",
                id
        );
        if (rows == 0) {
            throw new ResourceNotFoundException("Invoice Not Found");
        }
    }
}
