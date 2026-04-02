package com.lucasgrf.productservice.infrastructure.persistence.gateway;

import com.lucasgrf.productservice.domain.entity.Category;
import com.lucasgrf.productservice.domain.entity.Product;
import com.lucasgrf.productservice.domain.repository.CategoryRepository;
import com.lucasgrf.productservice.domain.repository.ProductRepository;
import com.lucasgrf.productservice.domain.valueobject.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for ProductDatabaseGateway and CategoryDatabaseGateway.
 * Uses a real PostgreSQL container via Testcontainers to validate actual
 * JPA persistence behavior rather than mocks.
 */
@DataJpaTest
@Testcontainers
@Import({ProductDatabaseGateway.class, CategoryDatabaseGateway.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("Product Database Gateway - Integration Tests")
class ProductDatabaseGatewayIntegrationTest {

    @Container
    @SuppressWarnings("resource")
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("ecommerce_test")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category savedCategory;

    @BeforeEach
    void setUp() {
        Category category = Category.builder()
                .id(CategoryId.generate())
                .name("Electronics")
                .slug(Slug.fromText("Electronics"))
                .description("Electronic goods")
                .build();
        savedCategory = categoryRepository.save(category);
    }

    @Test
    @DisplayName("Should persist and retrieve a product by ID")
    void shouldPersistAndRetrieveProductById() {
        // given
        Product product = Product.builder()
                .id(ProductId.generate())
                .name("Laptop Pro")
                .description("A powerful laptop")
                .price(new Money(new BigDecimal("2999.99")))
                .categoryId(savedCategory.getId())
                .stock(10)
                .active(true)
                .createdAt(LocalDateTime.now())
                .build();

        // when
        Product saved = productRepository.save(product);
        Optional<Product> found = productRepository.findById(saved.getId());

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Laptop Pro");
        assertThat(found.get().getPrice().amount()).isEqualByComparingTo(new BigDecimal("2999.99"));
    }

    @Test
    @DisplayName("Should return empty Optional when product not found by ID")
    void shouldReturnEmptyWhenProductNotFound() {
        // when
        Optional<Product> found = productRepository.findById(ProductId.generate());

        // then
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Should delete a product and make it no longer findable")
    void shouldDeleteProduct() {
        // given
        Product product = Product.builder()
                .id(ProductId.generate())
                .name("To Delete")
                .description("Will be deleted")
                .price(new Money(new BigDecimal("9.99")))
                .categoryId(savedCategory.getId())
                .stock(1)
                .active(true)
                .createdAt(LocalDateTime.now())
                .build();
        Product saved = productRepository.save(product);

        // when
        productRepository.delete(saved.getId());
        Optional<Product> found = productRepository.findById(saved.getId());

        // then
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Should list all categories including those from setup")
    void shouldFindAllCategories() {
        // given
        categoryRepository.save(Category.builder()
                .id(CategoryId.generate())
                .name("Clothing")
                .slug(Slug.fromText("Clothing"))
                .build());

        // when
        List<Category> all = categoryRepository.findAll();

        // then — electronics from @BeforeEach + clothing from above
        assertThat(all.size()).isGreaterThanOrEqualTo(2);
        assertThat(all).anyMatch(c -> "Electronics".equals(c.getName()));
        assertThat(all).anyMatch(c -> "Clothing".equals(c.getName()));
    }

    @Test
    @DisplayName("Should find category by exact slug value")
    void shouldFindCategoryBySlug() {
        // when
        Optional<Category> found = categoryRepository.findBySlug(Slug.fromText("Electronics"));

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Electronics");
    }

    @Test
    @DisplayName("Should not find category by wrong slug")
    void shouldNotFindCategoryByWrongSlug() {
        // when
        Optional<Category> found = categoryRepository.findBySlug(Slug.fromText("does-not-exist-99"));

        // then
        assertThat(found).isEmpty();
    }
}
