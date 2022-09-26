package com.example.sop_midterm.controller;

import com.example.sop_midterm.pojo.Product;
import com.example.sop_midterm.repo.ProductService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    public ProductService productService;

    public ProductController(){}

    @GetMapping("/getProduct")
    @RabbitListener(queues = "GetAllProductQueue")
    public List<Product> serviceGetProduct(){
        return productService.getAllProduct();
    }

    @RabbitListener(queues = "GetNameProductQueue")
    public Product serviceGetProductName(String name){
        return productService.getProductByName(name);
    }

    @RabbitListener(queues = "AddProductQueue")
    public boolean serviceAddProduct(Product prod){
        return productService.addProduct(prod);
    }

    @RabbitListener(queues = "UpdateProductQueue")
    public boolean serviceUpdateProduct(Product prod){
        return productService.updateProduct(prod);
    }

    @RabbitListener(queues = "DeleteProductQueue")
    public boolean serviceDeleteProduct(Product prod){
        return productService.deleteProduct(prod);
    }
}
