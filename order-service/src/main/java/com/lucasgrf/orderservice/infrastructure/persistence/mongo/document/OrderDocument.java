package com.lucasgrf.orderservice.infrastructure.persistence.mongo.document;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@Document(collection = "orders")
public class OrderDocument {
    @Id
    private String id;
    private String customerId;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private List<OrderItemDocument> items;
    private String status;
    private BigDecimal total;
    private BigDecimal shippingPrice;
    private String trackingCode;
}
