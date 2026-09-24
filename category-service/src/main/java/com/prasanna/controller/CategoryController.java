package com.prasanna.controller;


import com.prasanna.modal.Category;
import com.prasanna.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    //http://localhost:5003/api/categories/salon/2
    @GetMapping("/salon/{id}")
    public ResponseEntity<Set<Category>> getCategoriesBySalon(
            @PathVariable Long id
    ){
//        Set<Category> categories=categoryService.getAllCategoriesBySalon(id);
        Set<Category> categories=categoryService.getAllCategoryById(id);

        return ResponseEntity.ok(categories);
    }
    //http://localhost:5003/api/categories/2
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(
            @PathVariable Long id
    ) throws Exception {
        Category category=categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }




}
