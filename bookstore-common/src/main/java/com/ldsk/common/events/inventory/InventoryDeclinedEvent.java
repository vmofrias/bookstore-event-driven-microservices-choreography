package com.ldsk.common.events.inventory;

import java.time.Instant;
import java.util.UUID;

import com.ldsk.common.events.OrderSaga;

import lombok.Builder;
import lombok.Getter;


@Getter
public class InventoryDeclinedEvent extends OrderSaga {
	
	@Builder
	public InventoryDeclinedEvent(UUID orderId, Instant createdAt, Integer productId,
			Integer quantity, String message) {
		
		super(orderId, createdAt);
		
		this.productId = productId;
		this.quantity = quantity;
		this.message = message;
	}

	private Integer productId;
	private Integer quantity;
	private String message;
	
}
