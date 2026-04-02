package com.lucasgrf.productservice.infrastructure.persistence.gateway;

import com.lucasgrf.productservice.application.dto.ProductOutputDTO;
import com.lucasgrf.productservice.application.dto.ProductSearchCriteria;
import com.lucasgrf.productservice.domain.repository.ProductSearchRepository;
import com.lucasgrf.productservice.infrastructure.persistence.entity.ProductEntity;
import com.lucasgrf.productservice.infrastructure.persistence.repository.JpaProductRepository;
import com.lucasgrf.productservice.presentation.dto.PageResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductSearchDatabaseGateway implements ProductSearchRepository {

    private final JpaProductRepository jpaRepository;

    @Override
    public PageResponseDTO<ProductOutputDTO> search(ProductSearchCriteria searchCriteria) {
        Pageable pageable = PageRequest.of(searchCriteria.page(), searchCriteria.size());

        UUID categoryId = searchCriteria.categoryId() != null && !searchCriteria.categoryId().isBlank()
                ? UUID.fromString(searchCriteria.categoryId()) : null;

        Page<ProductEntity> resultPage = jpaRepository.searchProducts(
                searchCriteria.query(),
                categoryId,
                searchCriteria.minPrice(),
                searchCriteria.maxPrice(),
                pageable
        );

        List<ProductOutputDTO> content = resultPage.getContent().stream()
                .map(entity -> new ProductOutputDTO(
                        entity.getId().toString(),
                        entity.getName(),
                        entity.getDescription(),
                        entity.getPrice(),
                        entity.getCategoryId().toString(),
                        entity.getStock(),
                        entity.getImages(),
                        entity.getVariants(),
                        entity.getWeight(),
                        entity.getWidth(),
                        entity.getHeight(),
                        entity.getLength(),
                        entity.isActive()
                ))
                .collect(Collectors.toList());

        return new PageResponseDTO<>(
                content,
                resultPage.getNumber(),
                resultPage.getSize(),
                resultPage.getTotalElements(),
                resultPage.getTotalPages()
        );
    }
}
