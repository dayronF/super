package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.dto.Product.ProductMessage;
import com.example.demo.dto.Product.ProductRequest;
import com.example.demo.dto.Product.ProductResponse;
import com.example.demo.entity.ProductsEntity;
import com.example.demo.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductMessage createProduct(ProductRequest request) {

        ProductMessage message = new ProductMessage();

        ProductsEntity product = new ProductsEntity();

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setCategoryId(request.getCategoryId());
        product.setSupplierId(request.getSupplierId());

        productRepository.save(product);

        message.setProductMessage("Producto creado exitosamente");

        return message;
    }

    public List<ProductResponse> getAllProducts() {

        List<ProductsEntity> products = productRepository.findAll();
        List<ProductResponse> responses = new ArrayList<>();

        for (ProductsEntity product : products) {

            ProductResponse response = new ProductResponse();

            response.setId(product.getId());
            response.setName(product.getName());
            response.setPrice(product.getPrice());
            response.setStock(product.getStock());
            response.setCreatedAt(product.getCreatedAt());

            responses.add(response);
        }

        return responses;
    }

    public ProductResponse getById(Long id) {

        Optional<ProductsEntity> productOptional = productRepository.findById(id);

        if (productOptional.isEmpty()) {
            return null;
        }

        ProductsEntity product = productOptional.get();

        ProductResponse response = new ProductResponse();

        response.setId(product.getId());
        response.setName(product.getName());
        response.setPrice(product.getPrice());
        response.setStock(product.getStock());
        response.setCreatedAt(product.getCreatedAt());

        return response;
    }

    public ProductMessage updateProduct(Long id, ProductRequest request) {

        ProductMessage message = new ProductMessage();

        Optional<ProductsEntity> productOptional = productRepository.findById(id);

        if (productOptional.isEmpty()) {

            message.setProductMessage("Producto no encontrado");
            return message;
        }

        ProductsEntity product = productOptional.get();

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setCategoryId(request.getCategoryId());
        product.setSupplierId(request.getSupplierId());

        productRepository.save(product);

        message.setProductMessage("Producto actualizado correctamente");

        return message;
    }

    public ProductMessage deleteProduct(Long id) {

        ProductMessage message = new ProductMessage();

        Optional<ProductsEntity> productOptional = productRepository.findById(id);

        if (productOptional.isEmpty()) {

            message.setProductMessage("Producto no encontrado");
            return message;
        }

        productRepository.delete(productOptional.get());

        message.setProductMessage("Producto eliminado correctamente");

        return message;
    }

    public List<ProductResponse> getByCategory(Long categoryId) {

        List<ProductsEntity> products = productRepository.findByCategoryId(categoryId);
        List<ProductResponse> responses = new ArrayList<>();

        for (ProductsEntity product : products) {

            ProductResponse response = new ProductResponse();

            response.setId(product.getId());
            response.setName(product.getName());
            response.setPrice(product.getPrice());
            response.setStock(product.getStock());
            response.setCreatedAt(product.getCreatedAt());

            responses.add(response);
        }

        return responses;
    }

    public List<ProductResponse> getBySupplier(Long supplierId) {

        List<ProductsEntity> products = productRepository.findBySupplierId(supplierId);
        List<ProductResponse> responses = new ArrayList<>();

        for (ProductsEntity product : products) {

            ProductResponse response = new ProductResponse();

            response.setId(product.getId());
            response.setName(product.getName());
            response.setPrice(product.getPrice());
            response.setStock(product.getStock());
            response.setCreatedAt(product.getCreatedAt());

            responses.add(response);
        }

        return responses;
    }
}