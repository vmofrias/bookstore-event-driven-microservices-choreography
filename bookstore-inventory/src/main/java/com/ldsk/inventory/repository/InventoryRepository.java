package com.ldsk.inventory.repository;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.ldsk.common.events.inventory.InventoryStatus;
import com.ldsk.inventory.model.Inventory;

import reactor.core.publisher.Mono;

@Repository
public interface InventoryRepository extends ReactiveCrudRepository<Inventory, UUID> {

    Mono<Boolean> existsByOrderId(UUID orderId);

    Mono<Inventory> findByOrderIdAndStatus(UUID orderId, InventoryStatus status);
	
}
