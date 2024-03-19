package com.ldsk.order.repository;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.ldsk.order.model.OrderPayment;

import reactor.core.publisher.Mono;

@Repository
public interface OrderPaymentRepository extends ReactiveCrudRepository<OrderPayment, Integer> {

    Mono<OrderPayment> findByOrderId(UUID orderId);

}
