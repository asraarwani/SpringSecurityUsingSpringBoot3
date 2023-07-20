package com.jpmchase.www.controller;

import com.jpmchase.www.entity.Product;
import com.jpmchase.www.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String getGreetingMessage() {
        return "Welcome to Spring Security using Spring Boot 3.";
    }

    @GetMapping("/products/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Product getProductById(@PathVariable("id") Integer productId) {
        return productService.getProductById(productId);
    }

    @GetMapping("/products/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
}
