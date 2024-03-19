package com.ldsk.common.events.order;

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
public class OrderCreatedEvent implements OrderEvent {
	
	private UUID orderId; 
	private Instant createdAt;
	private Integer productId;
	private Integer customerId;
	private Integer quantity;
	private Integer unitPrice;
	private Integer totalAmount;
	
}
