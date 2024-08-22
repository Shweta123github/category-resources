package com.productmanagement.categoryresources.service;

import com.productmanagement.categoryresources.dto.CategoryDTO;
import com.productmanagement.categoryresources.dto.ProductDTO;
import com.productmanagement.categoryresources.entity.Product;
import com.productmanagement.categoryresources.exception.ResourceNotFoundException;
import com.productmanagement.categoryresources.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import static lombok.AccessLevel.PRIVATE;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@Log4j2
public class ProductService {
    ProductRepository productRepository;

    public List<ProductDTO> getAllProducts() {
        log.info("Fetching all products");
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductsByCategoryId(Long categoryId) {
        log.info("Fetching products by category id: {}", categoryId);
        return productRepository.findByCategoryId(categoryId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(Long id) {
        log.info("Fetching product by id: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return convertToDTO(product);
    }

    public ProductDTO createProduct(Product product) {
        log.info("Creating new product: {}", product);
        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    public ProductDTO updateProduct(Long id, Product productDetails) {
        log.info("Updating product with id: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        Product updatedProduct = productRepository.save(product);
        return convertToDTO(updatedProduct);
    }

    public void deleteProduct(Long id) {
        log.info("Deleting product with id: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        productRepository.delete(product);
    }

    private ProductDTO convertToDTO(Product product) {
        CategoryDTO categoryDTO = new CategoryDTO(
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getCategory().getDescription()
        );
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                categoryDTO,
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}




