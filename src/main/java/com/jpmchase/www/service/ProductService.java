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
            Product product = Product.builder().
                    id(i)
                    .name("Product " + i)
                    .description("Product description " + i)
                    .price(new Random().nextDouble()).build();
            products.add(product);
        });
    }

    public Product getProductById(int productId) {
        return products.stream().filter(i -> i.getId() == productId)
                .findAny()
                .orElse(Product.builder().build());
    }

    public List<Product> getAllProducts() {
        return products;
    }
}
