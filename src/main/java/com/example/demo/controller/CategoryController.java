package com.example.demo.controller;


import com.example.demo.dto.category.CategoryMessage;
import com.example.demo.dto.category.CategoryRequest;
import com.example.demo.dto.category.CategoryResponse;
import com.example.demo.enums.RolEnum;
import com.example.demo.service.ServiceCategory;
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
@RequestMapping("/categories")
public class CategoryController {

    private final ServiceCategory serviceCategory;

    @GetMapping("/list")
    public ResponseEntity<List<CategoryResponse>> listCategories() {
        try {
            List<CategoryResponse> categoryResponseList = serviceCategory.listCategories();
            return ResponseEntity.status(HttpStatus.OK).body(categoryResponseList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/find/{categoryId}")
    public ResponseEntity<CategoryResponse> findCategoryById(@PathVariable Long categoryId) {
        try {
            CategoryResponse categoryResponse = serviceCategory.findCategoryById(categoryId);
            if (categoryResponse == null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            return ResponseEntity.status(HttpStatus.OK).body(categoryResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<CategoryMessage> createCategory(
            @RequestBody CategoryRequest categoryRequest,
            HttpServletRequest httpServletRequest) {
        try {
            Long rolId = (Long) httpServletRequest.getAttribute("rolId");

            if (!rolId.equals(RolEnum.ADMIN.getId())) {
                CategoryMessage categoryMessage = new CategoryMessage();
                categoryMessage.setCategoryMessage("Unauthorized: admin role required");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(categoryMessage);
            }

            CategoryMessage categoryMessage = serviceCategory.createCategory(categoryRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(categoryMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<CategoryMessage> updateCategory(
            @PathVariable Long categoryId,
            @RequestBody CategoryRequest categoryRequest,
            HttpServletRequest httpServletRequest) {
        try {
            Long rolId = (Long) httpServletRequest.getAttribute("rolId");

            if (!rolId.equals(RolEnum.ADMIN.getId())) {
                CategoryMessage categoryMessage = new CategoryMessage();
                categoryMessage.setCategoryMessage("Unauthorized: admin role required");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(categoryMessage);
            }

            CategoryMessage categoryMessage = serviceCategory.updateCategory(categoryId, categoryRequest);
            return ResponseEntity.status(HttpStatus.OK).body(categoryMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<CategoryMessage> deleteCategory(
            @PathVariable Long categoryId,
            HttpServletRequest httpServletRequest) {
        try {
            Long rolId = (Long) httpServletRequest.getAttribute("rolId");

            if (!rolId.equals(RolEnum.ADMIN.getId())) {
                CategoryMessage categoryMessage = new CategoryMessage();
                categoryMessage.setCategoryMessage("Unauthorized: admin role required");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(categoryMessage);
            }

            CategoryMessage categoryMessage = serviceCategory.deleteCategory(categoryId);
            return ResponseEntity.status(HttpStatus.OK).body(categoryMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
