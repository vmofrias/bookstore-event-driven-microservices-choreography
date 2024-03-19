package com.ldsk.order.repository;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.ldsk.order.model.OrderInventory;

import reactor.core.publisher.Mono;

@Repository
public interface OrderInventoryRepository extends ReactiveCrudRepository<OrderInventory, Integer> {

	Mono<OrderInventory> findByOrderId(UUID orderId);
	
}
