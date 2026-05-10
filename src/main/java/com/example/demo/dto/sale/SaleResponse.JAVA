package com.example.demo.dto.sale;
import com.example.demo.dto.SaleItem.SaleItemResponse;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SaleResponse {
    private Integer id;
    private String cashierName;
    private BigDecimal total;
    private LocalDateTime createdAt;
    private List<SaleItemResponse> items;
}