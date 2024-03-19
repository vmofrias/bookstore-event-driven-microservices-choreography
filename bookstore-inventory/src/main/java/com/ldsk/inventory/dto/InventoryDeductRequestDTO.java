package com.ldsk.inventory.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryDeductRequestDTO {

	private UUID orderId;
	private Integer productId;
	private Integer quantity;
	
}
