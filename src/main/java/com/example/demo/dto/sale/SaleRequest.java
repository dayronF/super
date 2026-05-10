package com.example.demo.dto.sale;
import java.util.List;

import com.example.demo.dto.SaleItem.SaleItemRequest;

public class SaleRequest {
    private Integer cashierId;
    private List<SaleItemRequest> items;
}
