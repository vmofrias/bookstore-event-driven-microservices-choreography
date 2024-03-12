package com.ldsk.common.events.order;

import java.time.Instant;
import java.util.UUID;

import com.ldsk.common.events.OrderSaga;

import lombok.Builder;
import lombok.Getter;


@Getter
public class OrderCompletedEvent extends OrderSaga {
	
	@Builder
	public OrderCompletedEvent(UUID orderId, Instant createdAt) {
		
		super(orderId, createdAt);
	}

}
