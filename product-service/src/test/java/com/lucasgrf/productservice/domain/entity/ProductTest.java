package com.lucasgrf.productservice.domain.entity;

import com.lucasgrf.productservice.domain.valueobject.CategoryId;
import com.lucasgrf.productservice.domain.valueobject.Money;
import com.lucasgrf.productservice.domain.valueobject.ProductId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void shouldCreateProductSuccessfully() {
        Product product = Product.builder()
                .id(ProductId.generate())
                .categoryId(CategoryId.generate())
                .name("Camiseta Básica")
                .description("Camiseta 100% algodão preta")
                .price(new Money(new BigDecimal("49.90")))
                .stock(100)
                .images(List.of("http://example.com/img1.jpg"))
                .active(true)
                .build();

        assertNotNull(product.getId());
        assertEquals("Camiseta Básica", product.getName());
        assertEquals(new BigDecimal("49.90"), product.getPrice().amount());
        assertTrue(product.isActive());
    }

    @Test
    void shouldActivateAndDeactivateProduct() {
        Product product = Product.builder()
                .id(ProductId.generate())
                .active(true)
                .build();

        assertTrue(product.isActive());
        
        product.deactivate();
        assertFalse(product.isActive());
        
        product.activate();
        assertTrue(product.isActive());
    }

    @Test
    void shouldUpdateStock() {
        Product product = Product.builder()
                .id(ProductId.generate())
                .stock(10)
                .build();

        product.updateStock(25);
        assertEquals(25, product.getStock());
    }
}
