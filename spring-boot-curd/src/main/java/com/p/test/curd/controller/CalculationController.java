package com.p.test.curd.controller;

import com.p.test.curd.model.Books;
import com.p.test.curd.service.CalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * mark class as Controller
 */
@RestController
public class CalculationController {

    /**
     * autowire the BooksService class
     */
    @Autowired
    CalculationService booksService;

    /**
     * creating a get mapping that retrieves all the books detail from the database
     */
    @GetMapping("/calculation/{firstNumber}/{secondNumber}")
    private String getAllBooks(@PathVariable("firstNumber") String firstNumber, @PathVariable("secondNumber") String secondNumber) {

        return booksService.sum(firstNumber,secondNumber);
    }
}
