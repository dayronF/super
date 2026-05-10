package com.example.demo.dto.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ProductResponse {
    private Integer id;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private String categoryName;
    private String supplierName;
    private LocalDateTime createdAt;
}
