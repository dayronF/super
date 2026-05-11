package com.example.demo.controller;

import com.example.demo.dto.sale.SaleMessage;
import com.example.demo.dto.sale.SaleRequest;
import com.example.demo.dto.sale.SaleResponse;
import com.example.demo.enums.RolEnum;
import com.example.demo.service.SaleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sales")
public class SaleController {

    private final SaleService serviceSale;

    @GetMapping("/list")
    public ResponseEntity<List<SaleResponse>> listSales(HttpServletRequest httpServletRequest) {
        try {
            Long rolId = (Long) httpServletRequest.getAttribute("rolId");

            if (!rolId.equals(RolEnum.ADMIN.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }

            List<SaleResponse> saleResponseList = serviceSale.listSales();
            return ResponseEntity.status(HttpStatus.OK).body(saleResponseList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/find/{saleId}")
    public ResponseEntity<SaleResponse> findSaleById(
            @PathVariable Long saleId,
            HttpServletRequest httpServletRequest) {
        try {
            Long rolId = (Long) httpServletRequest.getAttribute("rolId");

            if (!rolId.equals(RolEnum.ADMIN.getId()) && !rolId.equals(RolEnum.CASHIER.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }

            SaleResponse saleResponse = serviceSale.findSaleById(saleId);
            if (saleResponse == null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            return ResponseEntity.status(HttpStatus.OK).body(saleResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/by-cashier/{cashierId}")
    public ResponseEntity<List<SaleResponse>> findSalesByCashier(
            @PathVariable Long cashierId,
            HttpServletRequest httpServletRequest) {
        try {
            Long rolId = (Long) httpServletRequest.getAttribute("rolId");

            if (!rolId.equals(RolEnum.ADMIN.getId()) && !rolId.equals(RolEnum.CASHIER.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }

            List<SaleResponse> saleResponseList = serviceSale.findSalesByCashier(cashierId);
            return ResponseEntity.status(HttpStatus.OK).body(saleResponseList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<SaleMessage> createSale(
            @RequestBody SaleRequest saleRequest,
            HttpServletRequest httpServletRequest) {
        try {
            Long rolId = (Long) httpServletRequest.getAttribute("rolId");

            if (!rolId.equals(RolEnum.CASHIER.getId()) && !rolId.equals(RolEnum.ADMIN.getId())) {
                SaleMessage saleMessage = new SaleMessage();
                saleMessage.setSaleMessage("Unauthorized: cashier or admin role required");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(saleMessage);
            }

            SaleMessage saleMessage = serviceSale.createSale(saleRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(saleMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}