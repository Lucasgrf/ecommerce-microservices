package com.lucasgrf.productservice.domain.repository;

import com.lucasgrf.productservice.application.dto.ProductSearchCriteria;
import com.lucasgrf.productservice.presentation.dto.PageResponseDTO;
import com.lucasgrf.productservice.application.dto.ProductOutputDTO;

public interface ProductSearchRepository {
    // Retorna a PageResponseDTO já, ou uma estrutura de página customizada do domain 
    // Para simplificar, vamos retornar o DTO de paginação direto ou introduzir uma classe genérica
    PageResponseDTO<ProductOutputDTO> search(ProductSearchCriteria criteria);
}
