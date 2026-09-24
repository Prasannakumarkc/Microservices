package com.prasanna.service;

import com.prasanna.dto.SalonDTO;
import com.prasanna.modal.Category;

import java.util.List;
import java.util.Set;

public interface CategoryService {

    Category saveCategory(Category category, SalonDTO salonDTO);
    Set<Category> getAllCategoryById(Long id);
    Category getCategoryById(Long id) throws Exception;
    void deleteCategoryById(Long id,Long salonId) throws Exception;
    Category findByIdAndSalonId(Long id, Long salonId) throws Exception;

//    Set<Category> getAllCategoriesBySalon(Long id);
}
