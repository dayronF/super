package com.example.demo.dto.Product;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductRequest {

    private String name;
    private BigDecimal price;
    private Integer stock;
    private Long categoryId;
    private Long supplierId;

}