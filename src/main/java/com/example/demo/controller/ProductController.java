package com.example.demo.controller;


import com.example.demo.dto.Product.ProductMessage;
import com.example.demo.dto.Product.ProductRequest;
import com.example.demo.dto.Product.ProductResponse;
import com.example.demo.enums.RolEnum;
import com.example.demo.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/list")
    public ResponseEntity<List<ProductResponse>> listProducts() {
        try {
            List<ProductResponse> productResponseList = productService.getAllProducts();
            return ResponseEntity.status(HttpStatus.OK).body(productResponseList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/find/{productId}")
    public ResponseEntity<ProductResponse> findProductById(@PathVariable Long productId) {
        try {
            ProductResponse productResponse = productService.getById(productId);
            if (productResponse == null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            return ResponseEntity.status(HttpStatus.OK).body(productResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<List<ProductResponse>> findByCategory(@PathVariable Long categoryId) {
        try {
            List<ProductResponse> productResponseList = productService.getByCategory(categoryId);
            return ResponseEntity.status(HttpStatus.OK).body(productResponseList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/by-supplier/{supplierId}")
    public ResponseEntity<List<ProductResponse>> findBySupplier(@PathVariable Long supplierId) {
        try {
            List<ProductResponse> productResponseList = productService.getBySupplier(supplierId);
            return ResponseEntity.status(HttpStatus.OK).body(productResponseList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ProductMessage> createProduct(
            @RequestBody ProductRequest productRequest,
            HttpServletRequest httpServletRequest) {
        try {
            Long rolId = (Long) httpServletRequest.getAttribute("rolId");

            if (!rolId.equals(RolEnum.ADMIN.getId()) && !rolId.equals(RolEnum.SUPPLIER.getId())) {
                ProductMessage productMessage = new ProductMessage();
                productMessage.setProductMessage("Unauthorized: admin or supplier role required");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(productMessage);
            }

            ProductMessage productMessage = productService.createProduct(productRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(productMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ProductMessage> updateProduct(
            @PathVariable Long productId,
            @RequestBody ProductRequest productRequest,
            HttpServletRequest httpServletRequest) {
        try {
            Long rolId = (Long) httpServletRequest.getAttribute("rolId");

            if (!rolId.equals(RolEnum.ADMIN.getId()) && !rolId.equals(RolEnum.SUPPLIER.getId())) {
                ProductMessage productMessage = new ProductMessage();
                productMessage.setProductMessage("Unauthorized: admin or supplier role required");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(productMessage);
            }

            ProductMessage productMessage = productService.updateProduct(productId, productRequest);
            return ResponseEntity.status(HttpStatus.OK).body(productMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ProductMessage> deleteProduct(
            @PathVariable Long productId,
            HttpServletRequest httpServletRequest) {
        try {
            Long rolId = (Long) httpServletRequest.getAttribute("rolId");

            if (!rolId.equals(RolEnum.ADMIN.getId()) && !rolId.equals(RolEnum.SUPPLIER.getId())) {
                ProductMessage productMessage = new ProductMessage();
                productMessage.setProductMessage("Unauthorized: admin or supplier role required");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(productMessage);
            }

            ProductMessage productMessage = productService.deleteProduct(productId);
            return ResponseEntity.status(HttpStatus.OK).body(productMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
