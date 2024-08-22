package com.productmanagement.categoryresources.service;

import com.productmanagement.categoryresources.dto.CategoryDTO;
import com.productmanagement.categoryresources.entity.Category;
import com.productmanagement.categoryresources.exception.ResourceNotFoundException;
import com.productmanagement.categoryresources.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTests {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;
    private CategoryDTO categoryDTO;

    @BeforeEach
    void setUp() {
        category = new Category(1L, "Category 1", "Description 1", null);
        categoryDTO = new CategoryDTO(1L, "Category 1", "Description 1");
    }

    @Test
    void getAllCategories_success() {
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category));

        List<CategoryDTO> categories = categoryService.getAllCategories();

        assertNotNull(categories);
        assertEquals(1, categories.size());
        assertEquals(categoryDTO, categories.get(0));
    }

    @Test
    void getCategoryById_success() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        CategoryDTO foundCategory = categoryService.getCategoryById(1L);

        assertNotNull(foundCategory);
        assertEquals(categoryDTO, foundCategory);
    }

    @Test
    void getCategoryById_notFound() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.getCategoryById(1L));
    }

    @Test
    void createCategory_success() {
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        CategoryDTO createdCategory = categoryService.createCategory(category);

        assertNotNull(createdCategory);
        assertEquals(categoryDTO, createdCategory);
    }

    @Test
    void updateCategory_success() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        CategoryDTO updatedCategory = categoryService.updateCategory(1L, category);

        assertNotNull(updatedCategory);
        assertEquals(categoryDTO, updatedCategory);
    }

    @Test
    void updateCategory_notFound() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.updateCategory(1L, category));
    }

    @Test
    void deleteCategory_success() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        doNothing().when(categoryRepository).delete(any(Category.class));

        categoryService.deleteCategory(1L);

        verify(categoryRepository, times(1)).delete(category);
    }

    @Test
    void deleteCategory_notFound() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.deleteCategory(1L));
    }
}
