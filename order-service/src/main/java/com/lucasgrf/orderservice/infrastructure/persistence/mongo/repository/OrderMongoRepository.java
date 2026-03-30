package com.lucasgrf.orderservice.infrastructure.persistence.mongo.repository;

import com.lucasgrf.orderservice.infrastructure.persistence.mongo.document.OrderDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMongoRepository extends MongoRepository<OrderDocument, String> {
}
