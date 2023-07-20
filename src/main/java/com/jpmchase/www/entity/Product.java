package com.jpmchase.www.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {

    private int id;
    private String name;
    private String description;
    private Double price;
}
