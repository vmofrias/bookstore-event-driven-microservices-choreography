package com.ldsk.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateRequestDTO {
	
	private Integer customerId;
	private Integer productId;
	private Integer quantity;
	private Integer unitPrice;

}
