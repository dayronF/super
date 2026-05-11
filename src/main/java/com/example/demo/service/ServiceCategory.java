package com.example.demo.service;

import com.example.demo.dto.Product.ProductResponse;
import com.example.demo.dto.category.CategoryMessage;
import com.example.demo.dto.category.CategoryRequest;
import com.example.demo.dto.category.CategoryResponse;
import com.example.demo.entity.CategoryEntity;
import com.example.demo.entity.ProductsEntity;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceCategory {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    

    public List<CategoryResponse> listCategories() {
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        List<CategoryResponse> categoryResponseList = new ArrayList<>();

        for (CategoryEntity categoryEntity : categoryEntityList) {
            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setId(categoryEntity.getId());
            categoryResponse.setName(categoryEntity.getName());
            categoryResponseList.add(categoryResponse);
        }

        return categoryResponseList;
    }

    public CategoryResponse findCategoryById(Long categoryId) {
        Optional<CategoryEntity> categoryOptional = categoryRepository.findById(categoryId);

        if (categoryOptional.isEmpty())
            return null;

        CategoryEntity categoryEntity = categoryOptional.get();

        List<ProductsEntity> productEntityList = productRepository.findByCategoryId(categoryEntity.getId());
        List<ProductResponse> productResponseList = new ArrayList<>();

        for (ProductsEntity productEntity : productEntityList) {
            ProductResponse productResponse = new ProductResponse();
            productResponse.setId(productEntity.getId());
            productResponse.setName(productEntity.getName());
            productResponse.setPrice(productEntity.getPrice());
            productResponse.setStock(productEntity.getStock());
            productResponseList.add(productResponse);
        }

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(categoryEntity.getId());
        categoryResponse.setName(categoryEntity.getName());
        categoryResponse.setProducts(productResponseList);

        return categoryResponse;
    }

    

    public CategoryMessage createCategory(CategoryRequest categoryRequest) {
        Optional<CategoryEntity> existingCategoryOptional = categoryRepository.findByName(categoryRequest.getName());

        if (existingCategoryOptional.isPresent()) {
            CategoryMessage categoryMessage = new CategoryMessage();
            categoryMessage.setCategoryMessage("Category already exists");
            return categoryMessage;
        }

        CategoryEntity newCategoryEntity = new CategoryEntity();
        newCategoryEntity.setName(categoryRequest.getName());
        categoryRepository.save(newCategoryEntity);

        CategoryMessage categoryMessage = new CategoryMessage();
        categoryMessage.setCategoryMessage("Category '" + newCategoryEntity.getName() + "' created successfully");
        return categoryMessage;
    }

    

    public CategoryMessage updateCategory(Long categoryId, CategoryRequest categoryRequest) {
        Optional<CategoryEntity> categoryOptional = categoryRepository.findById(categoryId);

        if (categoryOptional.isEmpty()) {
            CategoryMessage categoryMessage = new CategoryMessage();
            categoryMessage.setCategoryMessage("Category not found");
            return categoryMessage;
        }

        CategoryEntity categoryEntity = categoryOptional.get();
        categoryEntity.setName(categoryRequest.getName());
        categoryRepository.save(categoryEntity);

        CategoryMessage categoryMessage = new CategoryMessage();
        categoryMessage.setCategoryMessage("Category '" + categoryEntity.getName() + "' updated successfully");
        return categoryMessage;
    }

    

    public CategoryMessage deleteCategory(Long categoryId) {
        Optional<CategoryEntity> categoryOptional = categoryRepository.findById(categoryId);

        if (categoryOptional.isEmpty()) {
            CategoryMessage categoryMessage = new CategoryMessage();
            categoryMessage.setCategoryMessage("Category not found");
            return categoryMessage;
        }

        CategoryEntity categoryEntity = categoryOptional.get();
        categoryRepository.delete(categoryEntity);

        CategoryMessage categoryMessage = new CategoryMessage();
        categoryMessage.setCategoryMessage("Category deleted successfully");
        return categoryMessage;
    }
}