//package com.spring.billing_software.repository;
//
//
//import com.spring.billing_software.exception.ResourceNotFoundException;
//import com.spring.billing_software.entity.Customer;
//import org.springframework.stereotype.Repository;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Repository
//public class CustomerRepository {
//    private final ArrayList<Customer> list=new ArrayList<>();
//
//
//    public List<Customer> findAll(){
//        return list;
//    }
//
//    public Customer findById(int id){
//        return list.stream().filter
//                (p->p.getCustomerId()== id).findFirst().orElse(null);
//
//    }
//
//    public void save(Customer Customer){
//        list.add(Customer);
//    }
//
//    public void deleteById(int id ) {
//
//        //method 1
//
////       for (int i =0;i<list.size();i++){
////           if(list.get(i).getPid()==id){
////               list.remove(i);
////               break;
////           }
////       }
//
//
//        //method 2--collection framework function for List
//        list.removeIf(Customer -> Customer.getCustomerId()==id);
//    }
//
//    public boolean updateById(int id,Customer customer){
//        Customer existingCustomer=findById(id);
//        if(existingCustomer!=null){
//            existingCustomer.setName(customer.getName());
//            existingCustomer.setPhone(customer.getPhone());
//            return true;
//        }else{
//            throw new ResourceNotFoundException("Product Not Fouund");
//        }
//    }
//
//    public List<Customer> searchByName(String name){
//        return list.stream()
//                .filter(p -> p.getName().equalsIgnoreCase(name))
//                .toList();
//
//    }
//
////    public List<Customer> findByPriceBetween(int min, int max) {
////        return list.stream()
////                .filter(p -> p.getPhone() >= min && p.getPhone() <= max)
////                .collect(Collectors.toList());
////    }
//}
//

package com.spring.billing_software.repository;

import com.spring.billing_software.entity.Customer;
import com.spring.billing_software.exception.ResourceNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    public CustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Customer> rowMapper = (rs, rowNum) -> {
        Customer c = new Customer();
        c.setCustomerId(rs.getInt("customer_id"));
        c.setName(rs.getString("name"));
        c.setPhone(rs.getString("phone"));
        return c;
    };

    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT * FROM customer", rowMapper);
    }

    public Customer findById(int id) {
        List<Customer> list = jdbcTemplate.query(
                "SELECT * FROM customer WHERE customer_id = ?",
                rowMapper,
                id
        );
        return list.isEmpty() ? null : list.get(0);
    }

    public void save(Customer customer) {
        jdbcTemplate.update(
                "INSERT INTO customer(customer_id, name, phone,email ,address) VALUES (?, ?, ?, ?, ?)",
                customer.getCustomerId(),
                customer.getName(),
                customer.getPhone(),
                customer.getEmail(),
                customer.getAddress()
        );
    }

    public void deleteById(int id) {
        int rows = jdbcTemplate.update(
                "DELETE FROM customer WHERE customer_id = ?",
                id
        );
        if (rows == 0) {
            throw new ResourceNotFoundException("Customer Not Found");
        }
    }

    public boolean updateById(int id, Customer customer) {
        int rows = jdbcTemplate.update(
                "UPDATE customer SET name = ?, phone = ? WHERE customer_id = ?",
                customer.getName(),
                customer.getPhone(),
                id
        );
        if (rows == 0) {
            throw new ResourceNotFoundException("Customer Not Found");
        }
        return true;
    }

    public List<Customer> searchByName(String name) {
        return jdbcTemplate.query(
                "SELECT * FROM customer WHERE LOWER(name) = LOWER(?)",
                rowMapper,
                name
        );
    }
}