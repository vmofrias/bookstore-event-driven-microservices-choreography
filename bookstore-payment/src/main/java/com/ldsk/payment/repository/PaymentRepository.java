package com.ldsk.payment.repository;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.ldsk.common.events.payment.PaymentStatus;
import com.ldsk.payment.model.Payment;

import reactor.core.publisher.Mono;

@Repository
public interface PaymentRepository extends ReactiveCrudRepository<Payment, UUID> {

	Mono<Boolean> existsByOrderId(UUID orderId);
	Mono<Payment> findByOrderIdAndStatus(UUID orderId, PaymentStatus status);
	
}
