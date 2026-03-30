package com.lucasgrf.productservice.infrastructure.persistence.elasticsearch.repository;

import com.lucasgrf.productservice.infrastructure.persistence.elasticsearch.document.ProductIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductElasticRepository extends ElasticsearchRepository<ProductIndex, String> {
    // Custom query methods can be added here
}
