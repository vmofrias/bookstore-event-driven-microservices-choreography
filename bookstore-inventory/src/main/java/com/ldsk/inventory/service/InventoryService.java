package com.ldsk.inventory.service;

import java.util.UUID;

import com.ldsk.inventory.dto.InventoryDeductRequestDTO;
import com.ldsk.inventory.dto.OrderInventoryDTO;

import reactor.core.publisher.Mono;

public interface InventoryService {

    Mono<OrderInventoryDTO> deduct(InventoryDeductRequestDTO inventoryDeductRequestDTO);

    Mono<OrderInventoryDTO> restore(UUID orderId);
	
}
