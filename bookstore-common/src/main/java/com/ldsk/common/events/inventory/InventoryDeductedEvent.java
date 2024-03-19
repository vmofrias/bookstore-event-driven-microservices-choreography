package com.ldsk.common.events.inventory;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryDeductedEvent implements InventoryEvent {
	
	private UUID orderId; 
	private Instant createdAt;
	private UUID inventoryId;
	private Integer productId;
	private Integer quantity;
	
}
