package com.ldsk.inventory.dto;

import java.util.UUID;

import com.ldsk.common.events.inventory.InventoryStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderInventoryDTO {

	private UUID inventoryId;
	private UUID orderId;
	private Integer productId;
	private Integer quantity;
	private InventoryStatus status;
	
}
