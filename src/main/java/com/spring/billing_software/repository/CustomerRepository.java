package com.spring.billing_software.repository;


import com.spring.billing_software.exception.ResourceNotFoundException;
import com.spring.billing_software.entity.Customer;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CustomerRepository {
    private final ArrayList<Customer> list=new ArrayList<>();


    public List<Customer> findAll(){
        return list;
    }

    public Customer findById(int id){
        return list.stream().filter
                (p->p.getCustomerId()== id).findFirst().orElse(null);

    }

    public void save(Customer Customer){
        list.add(Customer);
    }

    public void deleteById(int id ) {

        //method 1

//       for (int i =0;i<list.size();i++){
//           if(list.get(i).getPid()==id){
//               list.remove(i);
//               break;
//           }
//       }


        //method 2--collection framework function for List
        list.removeIf(Customer -> Customer.getCustomerId()==id);
    }

    public boolean updateById(int id,Customer customer){
        Customer existingCustomer=findById(id);
        if(existingCustomer!=null){
            existingCustomer.setName(customer.getName());
            existingCustomer.setPhone(customer.getPhone());
            return true;
        }else{
            throw new ResourceNotFoundException("Product Not Fouund");
        }
    }

    public List<Customer> searchByName(String name){
        return list.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .toList();

    }

//    public List<Customer> findByPriceBetween(int min, int max) {
//        return list.stream()
//                .filter(p -> p.getPhone() >= min && p.getPhone() <= max)
//                .collect(Collectors.toList());
//    }
}

