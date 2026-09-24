package com.prasanna.controller;

import com.prasanna.dto.SalonDTO;
import com.prasanna.modal.Category;
import com.prasanna.service.CategoryService;
import com.prasanna.service.client.SalonFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories/salon-owner")
public class SalonCategoryController {
    private final CategoryService categoryService;
    private final SalonFeignClient salonFeignClient;

    //    //http://localhost:5003/api/categories/2
    @PostMapping()
    public ResponseEntity<Category> createCategory(
            @RequestBody Category category,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
//        SalonDTO salonDTO= new SalonDTO();
//        salonDTO.setId(1L);
        SalonDTO salonDTO = salonFeignClient.getSalonsByOwnerId(jwt).getBody();

        Category savedcategory=categoryService.saveCategory(category,salonDTO);
        return ResponseEntity.ok(savedcategory);
    }

    @GetMapping("/salon/{salonId}/category/{id}")
    public ResponseEntity<Category> getCategoriesByIdAndSalon(
            @PathVariable Long salonId,
            @PathVariable Long id
    ) throws Exception {
        Category category = categoryService.findByIdAndSalonId(id, salonId);
        return ResponseEntity.ok(category);
    }

    //http://localhost:5003/api/categories/salon-owner/2
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(
        @PathVariable Long id,
        @RequestHeader("Authorization") String jwt
    ) throws Exception {

//        SalonDTO salonDTO= new SalonDTO();
//        salonDTO.setId(1L);

        SalonDTO salonDTO = salonFeignClient.getSalonsByOwnerId(jwt).getBody();

        categoryService.deleteCategoryById(id,salonDTO.getId());
        return ResponseEntity.ok("category deleted successfully");
    }

}
