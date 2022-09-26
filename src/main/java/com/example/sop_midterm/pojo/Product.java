package com.example.sop_midterm.pojo;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document("product")
public class Product implements Serializable {
    private String id;
    private String productName;
    private Double productCost;
    private Double productProfit;
    private Double productPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductCost() {
        return productCost;
    }

    public void setProductCost(Double productCost) {
        this.productCost = productCost;
    }

    public Double getProductProfit() {
        return productProfit;
    }

    public void setProductProfit(Double productProfit) {
        this.productProfit = productProfit;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Product(){}

    public Product(String id, String productName, Double productCost, Double productProfit, Double productPrice){
        this.id = id;
        this.productName = productName;
        this.productCost = productCost;
        this.productProfit = productProfit;
        this.productPrice = productPrice;
    }

}
