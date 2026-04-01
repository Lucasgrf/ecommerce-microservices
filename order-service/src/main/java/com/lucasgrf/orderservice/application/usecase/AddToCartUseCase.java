package com.lucasgrf.orderservice.application.usecase;

import com.lucasgrf.orderservice.application.dto.AddToCartInputDTO;
import com.lucasgrf.orderservice.application.dto.CartItemOutputDTO;
import com.lucasgrf.orderservice.application.dto.CartOutputDTO;
import com.lucasgrf.orderservice.domain.entity.Cart;
import com.lucasgrf.orderservice.domain.entity.CartItem;
import com.lucasgrf.orderservice.domain.repository.CartRepository;
import com.lucasgrf.orderservice.domain.valueobject.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddToCartUseCase {

    private final CartRepository cartRepository;

    public CartOutputDTO execute(AddToCartInputDTO input) {
        Cart cart = cartRepository.findByCustomerId(input.customerId())
                .orElse(new Cart(input.customerId()));

        CartItem item = new CartItem(
                input.productId(),
                input.productName(),
                input.quantity(),
                new Money(input.unitPrice())
        );

        cart.addItem(item);
        Cart saved = cartRepository.save(cart);
        return toOutput(saved);
    }

    static CartOutputDTO toOutput(Cart cart) {
        List<CartItemOutputDTO> items = cart.getItems().stream()
                .map(i -> new CartItemOutputDTO(
                        i.getProductId(),
                        i.getProductName(),
                        i.getQuantity(),
                        i.getUnitPrice().amount(),
                        i.getSubtotal().amount()
                ))
                .toList();
        return new CartOutputDTO(cart.getCustomerId(), items, cart.getTotal().amount());
    }
}
