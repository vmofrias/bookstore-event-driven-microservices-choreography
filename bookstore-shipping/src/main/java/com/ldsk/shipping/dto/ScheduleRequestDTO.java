package com.ldsk.shipping.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleRequestDTO {

	private UUID orderId;
	private Integer productId;
	private Integer customerId;
	private Integer quantity;
	
}
