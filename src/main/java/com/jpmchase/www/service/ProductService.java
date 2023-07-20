package com.jpmchase.www.service;

import com.jpmchase.www.entity.Product;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Service
public class ProductService {

    private List<Product> products = new ArrayList<>();

    @PostConstruct
    public void createDummyListOfProducts() {
        IntStream.range(0, 100).forEach(i -> {
            Product product = new Product();
            product.setId(new Random().nextInt());
            product.setName("Product " + i);
            product.setDescription("Product description " + i);
            product.setPrice(new Random().nextDouble());
            products.add(product);
        });
    }

    public Product getProductById(int productId) {
        return products.stream().filter(i -> i.getId() == productId)
                .findAny()
                .orElseThrow(() -> new RuntimeException("Product with id " + productId + " not found."));
    }

    public List<Product> getAllProducts() {
        return products;
    }
}
