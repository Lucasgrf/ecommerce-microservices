package com.lucasgrf.productservice.infrastructure.persistence.mongo.document;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@Document(collection = "products")
public class ProductDocument {
    @Id
    private String id;
    private String categoryId;
    private String name;
    private String description;
    private BigDecimal price;
    private int stock;
    private List<String> images;
    private List<String> variants;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
