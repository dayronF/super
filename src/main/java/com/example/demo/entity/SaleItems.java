package com.example.demo.entity;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "sale_items")
public class SaleItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "sale_id")
    private int sale_id;
    @Column(name = "sale_id")
    private int product_id;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "unit_price", precision = 10, scale = 2)
    private BigDecimal unit_price;
}