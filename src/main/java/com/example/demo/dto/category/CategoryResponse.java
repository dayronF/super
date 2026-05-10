package com.example.demo.dto.category;

import java.util.List;

import com.example.demo.dto.Product.ProductResponse;

import lombok.Data;

@Data

public class CategoryResponse {
    private Integer id;
    private String name;
    private List<ProductResponse> products;

}
