package com.example.demo.dto.SaleItem;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class SaleItemResponse {
     private Integer id;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
}
