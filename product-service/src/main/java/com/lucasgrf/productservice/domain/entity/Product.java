package com.lucasgrf.productservice.domain.entity;

import com.lucasgrf.productservice.domain.valueobject.CategoryId;
import com.lucasgrf.productservice.domain.valueobject.Money;
import com.lucasgrf.productservice.domain.valueobject.ProductId;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class Product {
    private final ProductId id;
    private CategoryId categoryId;
    private String name;
    private String description;
    private Money price;
    private int stock;
    private List<String> images;
    // For fashion products, variants could be size/color, keeping it simple as a list of strings for MVP (e.g. "XL", "M", "S")
    private List<String> variants;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void activate() {
        this.active = true;
        this.updatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        this.active = false;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateStock(int newStock) {
        this.stock = newStock;
        this.updatedAt = LocalDateTime.now();
    }
    
    public void updateDetails(String name, String description, Money price, CategoryId categoryId, List<String> images, List<String> variants) {
        if(name != null && !name.trim().isEmpty()) this.name = name;
        if(description != null) this.description = description;
        if(price != null) this.price = price;
        if(categoryId != null) this.categoryId = categoryId;
        if(images != null) this.images = images;
        if(variants != null) this.variants = variants;
        this.updatedAt = LocalDateTime.now();
    }
}
