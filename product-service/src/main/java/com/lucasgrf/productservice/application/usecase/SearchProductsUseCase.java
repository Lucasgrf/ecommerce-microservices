package com.lucasgrf.productservice.application.usecase;

import com.lucasgrf.productservice.application.dto.ProductOutputDTO;
import com.lucasgrf.productservice.application.dto.ProductSearchCriteria;
import com.lucasgrf.productservice.domain.repository.ProductSearchRepository;
import com.lucasgrf.productservice.presentation.dto.PageResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchProductsUseCase {

    private final ProductSearchRepository productSearchRepository;

    public PageResponseDTO<ProductOutputDTO> execute(ProductSearchCriteria criteria) {
        return productSearchRepository.search(criteria);
    }
}
