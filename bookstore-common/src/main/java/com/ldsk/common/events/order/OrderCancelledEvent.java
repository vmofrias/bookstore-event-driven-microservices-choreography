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
public class OrderCancelledEvent implements OrderEvent {
	
	private UUID orderId; 
	private Instant createdAt;
	private String message;
	
}
