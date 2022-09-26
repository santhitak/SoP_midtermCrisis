package com.example.sop_midterm.repo;

import com.example.sop_midterm.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public ProductService(){}

    public ProductService(ProductRepository repo){
        this.productRepository = repo;
    }

    public List<Product> getAllProduct(){
        try {
            return productRepository.findAll();
        } catch (Exception e) {
            return null;
        }
    }

    public Product getProductByName(String name){
        try {
            return productRepository.findByName(name);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean addProduct(Product prod){
        try{
            prod.setId(null);
            productRepository.save(prod);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public boolean updateProduct(Product prod){
        try{
            productRepository.save(prod);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public boolean deleteProduct(Product prod){
        try{
            productRepository.delete(prod);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
