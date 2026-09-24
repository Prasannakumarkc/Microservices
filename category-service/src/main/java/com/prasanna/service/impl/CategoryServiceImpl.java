package com.prasanna.service.impl;

import com.prasanna.dto.SalonDTO;
import com.prasanna.modal.Category;
import com.prasanna.repository.CategoryRepository;
import com.prasanna.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

   private final CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(Category category, SalonDTO salonDTO) {
        Category newCategory =new Category();
        newCategory.setName(category.getName());
        newCategory.setSalonId(salonDTO.getId());
        newCategory.setImage(category.getImage());
        return categoryRepository.save(newCategory);
    }

    @Override
    public Set<Category> getAllCategoryById(Long id) {
        return categoryRepository.findBySalonId(id);
    }

    @Override
    public Category getCategoryById(Long id) throws Exception {
        Category category= categoryRepository.findById(id).orElse(null);

        if(category==null){
            throw new Exception("category not exist with this is" + id);
        }
        return category;
    }

    @Override
    public void deleteCategoryById(Long id,Long salonId) throws Exception {
       Category category= getCategoryById(id);
       if(!category.getSalonId().equals(salonId)){
           throw new Exception("you dont have permission to delete this category");
       }
        categoryRepository.deleteById(id);

    }

//    @Override
//    public Category findByIdAndSalonId(Long id, Long salonId) throws Exception {
//        Category category= categoryRepository.findByIdAndSalonId(id, salonId);
//        if (category == null) {
//            throw new Exception("Category not found...");
//        }
//        return category;
//    }

    //change
    @Override
    public Category findByIdAndSalonId(Long id, Long salonId) throws Exception {

        System.out.println("Category ID = " + id);
        System.out.println("Salon ID = " + salonId);

        Category category = categoryRepository.findByIdAndSalonId(id, salonId);

        System.out.println("Category result = " + category);

        if (category == null) {
            throw new Exception("Category not found...");
        }

        return category;
    }
}
