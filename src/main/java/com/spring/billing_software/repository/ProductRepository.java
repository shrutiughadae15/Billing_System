package com.spring.billing_software.repository;
import com.spring.billing_software.exception.ResourceNotFoundException;
import com.spring.billing_software.entity.Product;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProductRepository {
    private final ArrayList<Product> list=new ArrayList<>();


    public List<Product> findAll(){
        return list;
    }

    public Product findById(int id){
        return list.stream().filter
                (p->p.getProductId()== id).findFirst().orElse(null);

    }

    public void save(Product Product){
        list.add(Product);
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
        list.removeIf(Product -> Product.getProductId()==id);
    }

    public boolean updateById(int id,Product product){
        Product existingProduct=findById(id);
        if(existingProduct!=null){
            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            return true;
        }else{
            throw new ResourceNotFoundException("Product Not Fouund");
        }
    }

    public List<Product> searchByName(String name){
        return list.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .toList();

    }

    public List<Product> findByPriceBetween(int min, int max) {
        return list.stream()
                .filter(p -> p.getPrice() >= min && p.getPrice() <= max)
                .collect(Collectors.toList());
    }
}
