package com.example.sop_midterm.controller;

import com.example.sop_midterm.repo.CalculatorPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorPriceController {
    @Autowired
    private CalculatorPriceService calculatorPriceService;

    @RequestMapping(value = "/getPrice/{cost}/{profit}", method = RequestMethod.GET)
    public Double serviceGetProducts(@PathVariable("cost") Double cost, @PathVariable("profit") Double profit){
      return calculatorPriceService.getPrice(cost, profit);
    };
}
