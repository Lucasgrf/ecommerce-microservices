package com.lucasgrf.orderservice.infrastructure.persistence.gateway;

import com.lucasgrf.orderservice.domain.entity.Order;
import com.lucasgrf.orderservice.domain.entity.OrderItem;
import com.lucasgrf.orderservice.domain.entity.state.*;
import com.lucasgrf.orderservice.domain.repository.OrderRepository;
import com.lucasgrf.orderservice.domain.valueobject.Address;
import com.lucasgrf.orderservice.domain.valueobject.Money;
import com.lucasgrf.orderservice.domain.valueobject.OrderId;
import com.lucasgrf.orderservice.domain.valueobject.TrackingCode;
import com.lucasgrf.orderservice.infrastructure.persistence.mongo.document.OrderDocument;
import com.lucasgrf.orderservice.infrastructure.persistence.mongo.document.OrderItemDocument;
import com.lucasgrf.orderservice.infrastructure.persistence.mongo.repository.OrderMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderGateway implements OrderRepository {

    private final OrderMongoRepository mongoRepository;

    @Override
    public Order save(Order order) {
        OrderDocument doc = toDocument(order);
        OrderDocument savedDoc = mongoRepository.save(doc);
        return toDomain(savedDoc);
    }

    @Override
    public Optional<Order> findById(OrderId id) {
        return mongoRepository.findById(id.value()).map(this::toDomain);
    }

    @Override
    public Page<Order> findAll(Pageable pageable) {
        return mongoRepository.findAll(pageable).map(this::toDomain);
    }

    @Override
    public Page<Order> findByCustomerId(String customerId, Pageable pageable) {
        return mongoRepository.findByCustomerId(customerId, pageable).map(this::toDomain);
    }

    private OrderDocument toDocument(Order order) {
        return OrderDocument.builder()
                .id(order.getId().value())
                .customerId(order.getCustomerId())
                .street(order.getShippingAddress().street())
                .city(order.getShippingAddress().city())
                .state(order.getShippingAddress().state())
                .zipCode(order.getShippingAddress().zipCode())
                .status(order.getState().getName())
                .total(order.getTotal().amount())
                .shippingPrice(order.getShippingPrice().amount())
                .trackingCode(order.getTrackingCode() != null ? order.getTrackingCode().code() : null)
                .items(order.getItems().stream()
                        .map(item -> OrderItemDocument.builder()
                                .productId(item.getProductId())
                                .quantity(item.getQuantity())
                                .price(item.getPrice().amount())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    private Order toDomain(OrderDocument doc) {
        Order order = new Order(
                new OrderId(doc.getId()),
                doc.getCustomerId(),
                new Address(doc.getStreet(), doc.getCity(), doc.getState(), doc.getZipCode())
        );

        doc.getItems().forEach(itemDoc -> {
            OrderItem item = new OrderItem(
                    itemDoc.getProductId(),
                    itemDoc.getQuantity(),
                    new Money(itemDoc.getPrice())
            );
            order.addItem(item);
        });

        // Map status back to State Machine
        order.changeState(mapStatusToState(doc.getStatus()));
        
        if (doc.getTrackingCode() != null) {
            order.setTrackingCode(new TrackingCode(doc.getTrackingCode()));
        }

        if (doc.getShippingPrice() != null) {
            order.setShippingPrice(new Money(doc.getShippingPrice()));
        }

        return order;
    }

    private OrderState mapStatusToState(String status) {
        return switch (status) {
            case "PAID" -> new PaidState();
            case "SHIPPED" -> new ShippedState();
            case "DELIVERED" -> new DeliveredState();
            case "CANCELLED" -> new CancelledState("Persisted status");
            default -> new PendingPaymentState();
        };
    }
}
