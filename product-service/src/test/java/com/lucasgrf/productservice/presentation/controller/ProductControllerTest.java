package com.lucasgrf.productservice.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucasgrf.productservice.application.dto.CreateProductInputDTO;
import com.lucasgrf.productservice.application.dto.ProductOutputDTO;
import com.lucasgrf.productservice.application.usecase.CreateProductUseCase;
import com.lucasgrf.productservice.application.usecase.SearchProductsUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CreateProductUseCase createProductUseCase;

    @MockitoBean
    private SearchProductsUseCase searchProductsUseCase;

    @Test
    void shouldCreateProduct() throws Exception {
        CreateProductInputDTO input = new CreateProductInputDTO(
                "Camiseta", "Preta M", new BigDecimal("50.00"), "cat_123", 10, List.of(), List.of("M"),
                0.2, 12.0, 4.0, 18.0
        );
        ProductOutputDTO output = new ProductOutputDTO(
                "prod_123", "Camiseta", "Preta M", new BigDecimal("50.00"), "cat_123", 10, List.of(), List.of("M"),
                0.2, 12.0, 4.0, 18.0, true
        );

        when(createProductUseCase.execute(any(CreateProductInputDTO.class))).thenReturn(output);

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("prod_123"))
                .andExpect(jsonPath("$.name").value("Camiseta"));
    }
}
