package com.ldsk.order.dto;

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
public class OrderShipmentScheduleDTO {

	private UUID orderId;
    private Instant deliveryDate;
	
}
