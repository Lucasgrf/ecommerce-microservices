package com.lucasgrf.productservice.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucasgrf.productservice.application.dto.CategoryOutputDTO;
import com.lucasgrf.productservice.application.dto.CreateCategoryInputDTO;
import com.lucasgrf.productservice.application.usecase.CreateCategoryUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CreateCategoryUseCase createCategoryUseCase;

    @Test
    void shouldCreateCategory() throws Exception {
        CreateCategoryInputDTO input = new CreateCategoryInputDTO("Roupas", "Vestuário geral");
        CategoryOutputDTO output = new CategoryOutputDTO("cat_123", "Roupas", "roupas", "Vestuário geral");

        when(createCategoryUseCase.execute(any(CreateCategoryInputDTO.class))).thenReturn(output);

        mockMvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("cat_123"))
                .andExpect(jsonPath("$.slug").value("roupas"));
    }
}
