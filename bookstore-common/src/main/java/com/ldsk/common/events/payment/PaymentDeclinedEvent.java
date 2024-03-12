package com.ldsk.common.events.payment;

import java.time.Instant;
import java.util.UUID;

import com.ldsk.common.events.OrderSaga;

import lombok.Builder;
import lombok.Getter;


@Getter
public class PaymentDeclinedEvent extends OrderSaga {
	
	@Builder
	public PaymentDeclinedEvent(UUID orderId, Instant createdAt, Integer customerId, 
			Integer amount, String message) {
		
		super(orderId, createdAt);
		
		this.customerId = customerId;
		this.amount = amount;
		this.message = message;
	}

	private Integer customerId;
	private Integer amount;
	private String message;
	
}
