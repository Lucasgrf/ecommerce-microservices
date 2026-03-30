package com.lucasgrf.orderservice.application.usecase;

import com.lucasgrf.orderservice.application.dto.CreateOrderInputDTO;
import com.lucasgrf.orderservice.application.dto.OrderEvent;
import com.lucasgrf.orderservice.application.dto.OrderOutputDTO;
import com.lucasgrf.orderservice.application.port.OrderEventPublisher;
import com.lucasgrf.orderservice.application.port.PaymentServicePort;
import com.lucasgrf.orderservice.application.port.ProductServicePort;
import com.lucasgrf.orderservice.application.port.ShippingServicePort;
import com.lucasgrf.orderservice.domain.entity.Order;
import com.lucasgrf.orderservice.domain.entity.OrderItem;
import com.lucasgrf.orderservice.domain.repository.OrderRepository;
import com.lucasgrf.orderservice.domain.valueobject.Address;
import com.lucasgrf.orderservice.domain.valueobject.Money;
import com.lucasgrf.orderservice.domain.valueobject.OrderId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreateOrderUseCase {

    private final OrderRepository orderRepository;
    private final ProductServicePort productServicePort;
    private final ShippingServicePort shippingServicePort;
    private final PaymentServicePort paymentServicePort;
    private final OrderEventPublisher orderEventPublisher;

    public OrderOutputDTO execute(CreateOrderInputDTO input) {
        OrderId orderId = new OrderId(UUID.randomUUID().toString());
        Address address = new Address(input.street(), input.city(), input.state(), input.zipCode());

        Order order = new Order(orderId, input.customerId(), address);

        List<ShippingServicePort.ShippingItemInput> shippingItems = new ArrayList<>();

        input.items().forEach(itemDto -> {
            var product = productServicePort.getProductById(itemDto.productId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + itemDto.productId()));

            OrderItem item = new OrderItem(
                    itemDto.productId(),
                    itemDto.quantity(),
                    new Money(product.price())
            );
            order.addItem(item);

            shippingItems.add(new ShippingServicePort.ShippingItemInput(
                    product.id(),
                    itemDto.quantity(),
                    product.width(),
                    product.height(),
                    product.length(),
                    product.weight()
            ));
        });

        shippingServicePort.calculateShipping(input.zipCode(), shippingItems)
                .ifPresent(quote -> order.setShippingPrice(new Money(quote.price())));

        Order savedOrder = orderRepository.save(order);

        // Publish domain event
        OrderEvent orderEvent = new OrderEvent(
                savedOrder.getId().value(),
                savedOrder.getCustomerId(),
                savedOrder.getTotal().amount(),
                savedOrder.getItems().stream()
                        .map(i -> new OrderEvent.OrderEventItem(
                                i.getProductId(),
                                i.getQuantity(),
                                i.getPrice().amount()))
                        .collect(Collectors.toList())
        );
        orderEventPublisher.publish(orderEvent);

        // Generate Mercado Pago checkout URL (non-blocking — failure doesn't roll back order)
        String paymentUrl = paymentServicePort.createPaymentPreference(savedOrder);

        return OrderOutputDTO.fromWithPaymentUrl(savedOrder, paymentUrl);
    }
}
