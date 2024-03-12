package com.ldsk.common.events.inventory;

import java.time.Instant;
import java.util.UUID;

import com.ldsk.common.events.OrderSaga;

import lombok.Builder;
import lombok.Getter;


@Getter
public class InventoryRestoredEvent extends OrderSaga {
	
	@Builder
	public InventoryRestoredEvent(UUID orderId, Instant createdAt, Integer productId,
			Integer inventoryId, Integer quantity) {
		
		super(orderId, createdAt);
		
		this.productId = productId;
		this.inventoryId = inventoryId;
		this.quantity = quantity;
	}

	private Integer productId;
	private Integer inventoryId;
	private Integer quantity;
	
}
