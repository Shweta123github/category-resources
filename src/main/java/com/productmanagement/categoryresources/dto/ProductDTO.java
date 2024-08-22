package com.productmanagement.categoryresources.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    Long id;
    String name;
    String description;
    BigDecimal price;
    CategoryDTO category;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
