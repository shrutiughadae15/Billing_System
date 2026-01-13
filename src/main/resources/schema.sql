CREATE TABLE customer (
                          customer_id INT PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          phone VARCHAR(20),
                          email VARCHAR(100),
                          address VARCHAR(255)
);


CREATE TABLE invoice (
                         invoice_id INT AUTO_INCREMENT PRIMARY KEY,
                         invoice_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                         customer_id INT NOT NULL,
                         total_amount DECIMAL(10,2),
                         total_tax DECIMAL(10,2),
                         discount DECIMAL(10,2),
                         final_amount DECIMAL(10,2),

                         CONSTRAINT fk_invoice_customer
                             FOREIGN KEY (customer_id)
                                 REFERENCES customer(customer_id)
);

CREATE TABLE invoice_item (
                         item_id INT AUTO_INCREMENT PRIMARY KEY,
                         invoice_id INT NOT NULL,
                         product_id INT NOT NULL,
                         quantity INT NOT NULL,
                         price DECIMAL(10,2) NOT NULL,
                        tax_amount DECIMAL(10,2) DEFAULT 0.00,
                        total DECIMAL(10,2) AS (quantity * price + tax_amount) STORED,
                        FOREIGN KEY (invoice_id) REFERENCES invoice(invoice_id) ON DELETE CASCADE
    );
CREATE TABLE product (
                         product_id INT PRIMARY KEY,
                         name VARCHAR(100),
                         price DOUBLE,
                         stock_quantity INT,
                         gst_percentage DOUBLE
);

