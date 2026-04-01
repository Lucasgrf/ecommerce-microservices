package com.lucasgrf.productservice.application.usecase;

import com.lucasgrf.productservice.application.dto.StockReservationRequestDTO;
import com.lucasgrf.productservice.domain.entity.Product;
import com.lucasgrf.productservice.domain.exception.DomainException;
import com.lucasgrf.productservice.domain.exception.ProductNotFoundException;
import com.lucasgrf.productservice.domain.repository.ProductRepository;
import com.lucasgrf.productservice.domain.valueobject.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReserveStockUseCase {

    private final ProductRepository productRepository;

    /**
     * Reserves stock for a list of products. Each product's stock is decremented by the requested quantity.
     * This is transactional — if any product has insufficient stock, the entire operation is rolled back.
     */
    @Transactional
    public void execute(StockReservationRequestDTO request) {
        for (Map.Entry<String, Integer> entry : request.items().entrySet()) {
            ProductId productId = new ProductId(entry.getKey());
            int requestedQty  = entry.getValue();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException(entry.getKey()));

            if (product.getStock() < requestedQty) {
                throw new DomainException(
                        "Insufficient stock for product '" + product.getName()
                        + "'. Available: " + product.getStock()
                        + ", requested: " + requestedQty
                );
            }

            product.updateStock(product.getStock() - requestedQty);
            productRepository.save(product);
        }
    }
}
