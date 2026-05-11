package com.example.demo.service;

import com.example.demo.dto.SaleItem.SaleItemRequest;
import com.example.demo.dto.SaleItem.SaleItemResponse;
import com.example.demo.dto.sale.SaleMessage;
import com.example.demo.dto.sale.SaleRequest;
import com.example.demo.dto.sale.SaleResponse;
import com.example.demo.entity.ProductsEntity;
import com.example.demo.entity.SaleItemsEntity;
import com.example.demo.entity.SalesEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.SaleItemsRepository;
import com.example.demo.repository.SaleRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceSale {

    private final SaleRepository saleRepository;
    private final SaleItemsRepository saleItemsRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public List<SaleResponse> listSales() {
        List<SalesEntity> salesEntityList = saleRepository.findAll();
        List<SaleResponse> saleResponseList = new ArrayList<>();

        for (SalesEntity salesEntity : salesEntityList) {
            saleResponseList.add(buildSaleResponse(salesEntity));
        }

        return saleResponseList;
    }

    public SaleResponse findSaleById(Long saleId) {
        Optional<SalesEntity> salesEntityOptional = saleRepository.findById(saleId);

        if (salesEntityOptional.isEmpty())
            return null;

        return buildSaleResponse(salesEntityOptional.get());
    }

    public List<SaleResponse> findSalesByCashier(Long cashierId) {
        List<SalesEntity> salesEntityList = saleRepository.findByCashierId(cashierId);
        List<SaleResponse> saleResponseList = new ArrayList<>();

        for (SalesEntity salesEntity : salesEntityList) {
            saleResponseList.add(buildSaleResponse(salesEntity));
        }

        return saleResponseList;
    }

    public SaleMessage createSale(SaleRequest saleRequest) {
        Optional<UserEntity> cashierOptional = userRepository.findById(saleRequest.getCashierId().longValue());

        if (cashierOptional.isEmpty()) {
            SaleMessage saleMessage = new SaleMessage();
            saleMessage.setSaleMessage("Cashier not found");
            return saleMessage;
        }

        if (saleRequest.getItems() == null || saleRequest.getItems().isEmpty()) {
            SaleMessage saleMessage = new SaleMessage();
            saleMessage.setSaleMessage("Sale must have at least one item");
            return saleMessage;
        }

        BigDecimal saleTotal = BigDecimal.ZERO;
        List<ProductsEntity> productEntityList = new ArrayList<>();

        for (SaleItemRequest saleItemRequest : saleRequest.getItems()) {
            Optional<ProductsEntity> productOptional = productRepository.findById(saleItemRequest.getProductId().longValue());

            if (productOptional.isEmpty()) {
                SaleMessage saleMessage = new SaleMessage();
                saleMessage.setSaleMessage("Product with id " + saleItemRequest.getProductId() + " not found");
                return saleMessage;
            }

            ProductsEntity productEntity = productOptional.get();

            if (productEntity.getStock() < saleItemRequest.getQuantity()) {
                SaleMessage saleMessage = new SaleMessage();
                saleMessage.setSaleMessage("Insufficient stock for product '" + productEntity.getName() + "'");
                return saleMessage;
            }

            BigDecimal subtotal = productEntity.getPrice()
                    .multiply(BigDecimal.valueOf(saleItemRequest.getQuantity()));
            saleTotal = saleTotal.add(subtotal);

            productEntityList.add(productEntity);
        }

        SalesEntity newSalesEntity = new SalesEntity();
        newSalesEntity.setCashierId(saleRequest.getCashierId().longValue());
        newSalesEntity.setTotal(saleTotal);
        newSalesEntity.setCreatedAt(LocalDateTime.now());
        saleRepository.save(newSalesEntity);

        for (int i = 0; i < saleRequest.getItems().size(); i++) {
            SaleItemRequest saleItemRequest = saleRequest.getItems().get(i);
            ProductsEntity productEntity = productEntityList.get(i);

            SaleItemsEntity newSaleItemEntity = new SaleItemsEntity();
            newSaleItemEntity.setSaleId(newSalesEntity.getId());
            newSaleItemEntity.setProductId(productEntity.getId());
            newSaleItemEntity.setQuantity(saleItemRequest.getQuantity());
            newSaleItemEntity.setUnitPrice(productEntity.getPrice());
            saleItemsRepository.save(newSaleItemEntity);

            productEntity.setStock(productEntity.getStock() - saleItemRequest.getQuantity());
            productRepository.save(productEntity);
        }

        SaleMessage saleMessage = new SaleMessage();
        saleMessage.setSaleMessage("Sale created successfully with total " + saleTotal);
        return saleMessage;
    }

    private SaleResponse buildSaleResponse(SalesEntity salesEntity) {
        String cashierName = userRepository.findById(salesEntity.getCashierId())
                .map(UserEntity::getName)
                .orElse("Unknown");

        List<SaleItemsEntity> saleItemsEntityList = saleItemsRepository.findBySaleId(salesEntity.getId());
        List<SaleItemResponse> saleItemResponseList = new ArrayList<>();

        for (SaleItemsEntity saleItemsEntity : saleItemsEntityList) {
            String productName = productRepository.findById(saleItemsEntity.getProductId())
                    .map(ProductsEntity::getName)
                    .orElse("Unknown");

            BigDecimal subtotal = saleItemsEntity.getUnitPrice()
                    .multiply(BigDecimal.valueOf(saleItemsEntity.getQuantity()));

            SaleItemResponse saleItemResponse = new SaleItemResponse();
            saleItemResponse.setId(saleItemsEntity.getId());
            saleItemResponse.setProductName(productName);
            saleItemResponse.setQuantity(saleItemsEntity.getQuantity());
            saleItemResponse.setUnitPrice(saleItemsEntity.getUnitPrice());
            saleItemResponse.setSubtotal(subtotal);
            saleItemResponseList.add(saleItemResponse);
        }

        SaleResponse saleResponse = new SaleResponse();
        saleResponse.setId(salesEntity.getId());
        saleResponse.setCashierName(cashierName);
        saleResponse.setTotal(salesEntity.getTotal());
        saleResponse.setCreatedAt(salesEntity.getCreatedAt());
        saleResponse.setItems(saleItemResponseList);

        return saleResponse;
    }
}