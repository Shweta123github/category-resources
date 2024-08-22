package com.productmanagement.categoryresources.service;

import com.productmanagement.categoryresources.dto.CategoryDTO;
import com.productmanagement.categoryresources.dto.ProductDTO;
import com.productmanagement.categoryresources.entity.Category;
import com.productmanagement.categoryresources.entity.Product;
import com.productmanagement.categoryresources.exception.ResourceNotFoundException;
import com.productmanagement.categoryresources.repository.ProductRepository;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@FieldDefaults(level = PRIVATE)
@Log4j2
public class ProductServiceTests {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    private Category category;
    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        category = new Category(1L, "Category1", "Description1", null);
        product = new Product(1L, "Product1", "Description1", BigDecimal.valueOf(100.00), category, null, null);
        CategoryDTO categoryDTO = new CategoryDTO(1L, "Category1", "Description1");
        productDTO = new ProductDTO(1L, "Product1", "Description1", BigDecimal.valueOf(100.00), categoryDTO, null, null);
    }

    @Test
    public void testGetAllProducts() {
        // Given
        Product product2 = new Product(2L, "Product2", "Description2", BigDecimal.valueOf(200.00), category, null, null);
        List<Product> products = Arrays.asList(product, product2);

        when(productRepository.findAll()).thenReturn(products);

        // When
        List<ProductDTO> result = productService.getAllProducts();

        // Then
        assertEquals(2, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void testGetProductsByCategoryId() {
        // Given
        Product product2 = new Product(2L, "Product2", "Description2", BigDecimal.valueOf(200.00), category, null, null);
        List<Product> products = Arrays.asList(product, product2);

        when(productRepository.findByCategoryId(1L)).thenReturn(products);

        // When
        List<ProductDTO> result = productService.getProductsByCategoryId(1L);

        // Then
        assertEquals(2, result.size());
        verify(productRepository, times(1)).findByCategoryId(1L);
    }

    @Test
    public void testGetProductById() {
        // Given
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // When
        ProductDTO result = productService.getProductById(1L);

        // Then
        assertNotNull(result);
        assertEquals("Product1", result.getName());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetProductByIdNotFound() {
        // Given
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(1L));
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateProduct() {
        // Given
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // When
        ProductDTO result = productService.createProduct(product);

        // Then
        assertNotNull(result);
        assertEquals(productDTO, result);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testUpdateProduct() {
        // Given
        Product updatedProduct = new Product(1L, "UpdatedProduct", "UpdatedDescription", BigDecimal.valueOf(150.00), category, null, null);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        // When
        ProductDTO result = productService.updateProduct(1L, updatedProduct);

        // Then
        assertNotNull(result);
        assertEquals("UpdatedProduct", result.getName());
        assertEquals("UpdatedDescription", result.getDescription());
        assertEquals(BigDecimal.valueOf(150.00), result.getPrice());
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(updatedProduct);
    }

    @Test
    public void testUpdateProductNotFound() {
        // Given
        Product updatedProduct = new Product(1L, "UpdatedProduct", "UpdatedDescription", BigDecimal.valueOf(150.00), category, null, null);
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> productService.updateProduct(1L, updatedProduct));
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteProduct() {
        // Given
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        doNothing().when(productRepository).delete(product);

        // When
        productService.deleteProduct(1L);

        // Then
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    public void testDeleteProductNotFound() {
        // Given
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(1L));
        verify(productRepository, times(1)).findById(1L);
    }
}

