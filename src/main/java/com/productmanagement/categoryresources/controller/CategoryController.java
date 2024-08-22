package com.productmanagement.categoryresources.controller;

import com.productmanagement.categoryresources.dto.CategoryDTO;
import com.productmanagement.categoryresources.entity.Category;
import com.productmanagement.categoryresources.service.CategoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@Log4j2
public class CategoryController {
    CategoryService categoryService;

    @GetMapping
    public List<CategoryDTO> getAllCategories() {
        log.info("Getting all categories");
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        log.info("Getting category by id: {}", id);
        CategoryDTO category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO category) {
        log.info("Creating new category: {}", category);
        CategoryDTO newCategory = categoryService.createCategory(new Category(
                category.getId(),
                category.getName(),
                category.getDescription(),
                null
        ));
        return ResponseEntity.status(HttpStatus.CREATED).body(newCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDetails) {
        log.info("Updating category with id: {}", id);
        CategoryDTO updatedCategory = categoryService.updateCategory(id, new Category(
                categoryDetails.getId(),
                categoryDetails.getName(),
                categoryDetails.getDescription(),
                null
        ));
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        log.info("Deleting category with id: {}", id);
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Data deleted successfully");
    }

}

