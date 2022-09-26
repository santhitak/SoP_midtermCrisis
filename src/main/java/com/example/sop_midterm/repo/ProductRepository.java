package com.example.sop_midterm.repo;

import com.example.sop_midterm.pojo.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    @Query(value = "{ productName : '?0' }")
    Product findByName(String productName);
}
