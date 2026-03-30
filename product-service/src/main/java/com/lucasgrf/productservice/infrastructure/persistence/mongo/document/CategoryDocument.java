package com.lucasgrf.productservice.infrastructure.persistence.mongo.document;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@Document(collection = "categories")
public class CategoryDocument {
    @Id
    private String id;
    private String name;
    
    @Indexed(unique = true)
    private String slug;
    
    private String description;
}
