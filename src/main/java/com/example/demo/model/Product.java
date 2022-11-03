package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;


@Entity
@Table(name = "products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private Double price;

    public Product(String productName, Double price) {
        this.productName = productName;
        this.price = price;
    }

    public Product() {

    }
}
