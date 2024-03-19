package com.ldsk.order.dto;

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

	private UUID orderId;
    private UUID inventoryId;
    private InventoryStatus status;
    private String message;
	
}
