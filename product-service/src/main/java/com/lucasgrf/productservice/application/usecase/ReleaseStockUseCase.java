package com.lucasgrf.productservice.application.usecase;

import com.lucasgrf.productservice.application.dto.StockReservationRequestDTO;
import com.lucasgrf.productservice.domain.entity.Product;
import com.lucasgrf.productservice.domain.exception.ProductNotFoundException;
import com.lucasgrf.productservice.domain.repository.ProductRepository;
import com.lucasgrf.productservice.domain.valueobject.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReleaseStockUseCase {

    private final ProductRepository productRepository;

    /**
     * Releases (increments) stock for a list of products.
     * Called when an order is cancelled or a reservation must be undone.
     */
    @Transactional
    public void execute(StockReservationRequestDTO request) {
        for (Map.Entry<String, Integer> entry : request.items().entrySet()) {
            ProductId productId = new ProductId(entry.getKey());
            int qty = entry.getValue();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException(entry.getKey()));

            product.updateStock(product.getStock() + qty);
            productRepository.save(product);
        }
    }
}
