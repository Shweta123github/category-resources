package com.productmanagement.categoryresources.controller;


import com.productmanagement.categoryresources.dto.ProductDTO;
import com.productmanagement.categoryresources.entity.Category;
import com.productmanagement.categoryresources.entity.Product;
import com.productmanagement.categoryresources.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;


import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@Log4j2
public class ProductController {
    ProductService productService;

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        log.info("Getting all products");
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        log.info("Getting product by id: {}", id);
        ProductDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/category/{categoryId}")
    public List<ProductDTO> getProductsByCategoryId(@PathVariable Long categoryId) {
        log.info("Getting products by category id: {}", categoryId);
        return productService.getProductsByCategoryId(categoryId);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO product) {
        log.info("Creating new product: {}", product);
        ProductDTO newProduct = productService.createProduct(new Product(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                new Category(
                        product.getCategory().getId(),
                        product.getCategory().getName(),
                        product.getCategory().getDescription(),

                        null
                ),
                product.getCreatedAt(),
                product.getUpdatedAt()
        ));
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDetails) {
        log.info("Updating product with id: {}", id);
        ProductDTO updatedProduct = productService.updateProduct(id, new Product(
                productDetails.getId(),
                productDetails.getName(),
                productDetails.getDescription(),
                productDetails.getPrice(),
                new Category(
                        productDetails.getCategory().getId(),
                        productDetails.getCategory().getName(),
                        productDetails.getCategory().getDescription(),
                        null
                ),
                productDetails.getCreatedAt(),
                productDetails.getUpdatedAt()
        ));
        return ResponseEntity.ok(updatedProduct);
    }

        @DeleteMapping("/{id}")
        public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
            log.info("Deleting category with id: {}", id);
            productService.deleteProduct(id);
            return ResponseEntity.ok("Data deleted successfully");
        }
    }








