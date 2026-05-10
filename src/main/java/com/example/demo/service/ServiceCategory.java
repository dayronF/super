package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.CategoryEntity;
import com.example.demo.repository.CategoryRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ServiceCategory {
    
    private final CategoryRepository categoryRepository;

    public CatgoryResponse CeateCategory(CategoryRequest Category){

        if (categoryRepository.existsByName(Category.getName())) {
            return new CategoryMessage("Ya existe una categoría con ese nombre");
        }

        CategoryEntity category = new CategoryEntity();
        category.setName(Category.getName());
        categoryRepository.save(category);

        return new CategoryMessage("Categoría " + category.getName() + " creada exitosamente");
    }

    }

