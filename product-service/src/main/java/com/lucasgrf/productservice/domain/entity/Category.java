package com.lucasgrf.productservice.domain.entity;

import com.lucasgrf.productservice.domain.valueobject.CategoryId;
import com.lucasgrf.productservice.domain.valueobject.Slug;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Category {
    private final CategoryId id;
    private String name;
    private Slug slug;
    private String description;

    public void updateName(String newName) {
        if (newName != null && !newName.trim().isEmpty()) {
            this.name = newName;
            this.slug = Slug.fromText(newName);
        }
    }

    public void updateDescription(String description) {
        this.description = description;
    }
}
