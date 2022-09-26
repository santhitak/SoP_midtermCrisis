package com.example.sop_midterm.repo;

import com.example.sop_midterm.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculatorPriceService {

    public Double getPrice(Double cost, Double profit){
        return cost+profit;
    }

}
