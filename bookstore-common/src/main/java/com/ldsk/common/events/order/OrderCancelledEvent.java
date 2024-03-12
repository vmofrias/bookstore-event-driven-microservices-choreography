package com.ldsk.common.events.order;

import java.time.Instant;
import java.util.UUID;

import com.ldsk.common.events.OrderSaga;

import lombok.Builder;
import lombok.Getter;


@Getter
public class OrderCancelledEvent extends OrderSaga {
	
	@Builder
	public OrderCancelledEvent(UUID orderId, Instant createdAt, String message) {
		
		super(orderId, createdAt);
		
		this.message = message;
	}

	private String message;
	
}
