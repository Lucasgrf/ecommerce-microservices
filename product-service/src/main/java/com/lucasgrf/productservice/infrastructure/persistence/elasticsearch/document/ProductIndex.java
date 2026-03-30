package com.lucasgrf.productservice.infrastructure.persistence.elasticsearch.document;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@Document(indexName = "products")
public class ProductIndex {
    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String categoryId;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String name;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String description;

    @Field(type = FieldType.Double)
    private BigDecimal price;

    @Field(type = FieldType.Integer)
    private int stock;
    
    @Field(type = FieldType.Keyword)
    private List<String> images;

    @Field(type = FieldType.Boolean)
    private boolean active;
}
