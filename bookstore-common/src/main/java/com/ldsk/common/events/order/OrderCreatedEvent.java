package com.ldsk.common.events.order;

import java.time.Instant;
import java.util.UUID;

import com.ldsk.common.events.OrderSaga;

import lombok.Builder;
import lombok.Getter;


@Getter
public class OrderCreatedEvent extends OrderSaga {
	
	@Builder
	public OrderCreatedEvent(UUID orderId, Instant createdAt, Integer productId,
			Integer customerId, Integer quantity, Integer unitPrice, Integer totalAmount) {
		
		super(orderId, createdAt);
		
		this.productId = productId;
		this.customerId = customerId;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.totalAmount = totalAmount;
	}

	private Integer productId;
	private Integer customerId;
	private Integer quantity;
	private Integer unitPrice;
	private Integer totalAmount;
	
}
