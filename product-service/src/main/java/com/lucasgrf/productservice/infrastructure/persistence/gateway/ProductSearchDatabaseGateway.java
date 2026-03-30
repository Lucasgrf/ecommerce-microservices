package com.lucasgrf.productservice.infrastructure.persistence.gateway;

import com.lucasgrf.productservice.application.dto.ProductOutputDTO;
import com.lucasgrf.productservice.application.dto.ProductSearchCriteria;
import com.lucasgrf.productservice.domain.repository.ProductSearchRepository;
import com.lucasgrf.productservice.infrastructure.persistence.elasticsearch.document.ProductIndex;
import com.lucasgrf.productservice.presentation.dto.PageResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductSearchDatabaseGateway implements ProductSearchRepository {

    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public PageResponseDTO<ProductOutputDTO> search(ProductSearchCriteria searchCriteria) {
        Pageable pageable = PageRequest.of(searchCriteria.page(), searchCriteria.size());

        Criteria criteria = new Criteria();

        if (searchCriteria.query() != null && !searchCriteria.query().isBlank()) {
            criteria = new Criteria("name").matches(searchCriteria.query())
                    .or("description").matches(searchCriteria.query());
        }

        if (searchCriteria.categoryId() != null && !searchCriteria.categoryId().isBlank()) {
            criteria = criteria.and("categoryId").is(searchCriteria.categoryId());
        }

        if (searchCriteria.minPrice() != null) {
            criteria = criteria.and("price").greaterThanEqual(searchCriteria.minPrice());
        }

        if (searchCriteria.maxPrice() != null) {
            criteria = criteria.and("price").lessThanEqual(searchCriteria.maxPrice());
        }

        Query query = new CriteriaQuery(criteria).setPageable(pageable);

        SearchHits<ProductIndex> searchHits = elasticsearchOperations.search(query, ProductIndex.class);

        List<ProductOutputDTO> content = searchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                // In a real scenario, we might want to map directly from index to output DTO, 
                // but since the DTO expects domain format, we simplify here.
                .map(index -> new ProductOutputDTO(
                        index.getId(),
                        index.getName(),
                        index.getDescription(),
                        index.getPrice(),
                        index.getCategoryId(),
                        index.getStock(),
                        index.getImages(),
                        List.of(), // Variants aren't in elastic summary MVP
                        index.isActive()
                ))
                .collect(Collectors.toList());

        long totalElements = searchHits.getTotalHits();
        int totalPages = (int) Math.ceil((double) totalElements / searchCriteria.size());

        return new PageResponseDTO<>(content, searchCriteria.page(), searchCriteria.size(), totalElements, totalPages);
    }
}
