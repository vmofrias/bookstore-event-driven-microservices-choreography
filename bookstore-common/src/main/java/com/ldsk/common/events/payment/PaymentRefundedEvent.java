package com.ldsk.common.events.payment;

import java.time.Instant;
import java.util.UUID;

import com.ldsk.common.events.OrderSaga;

import lombok.Builder;
import lombok.Getter;


@Getter
public class PaymentRefundedEvent extends OrderSaga {
	
	@Builder
	public PaymentRefundedEvent(UUID orderId, Instant createdAt, UUID paymentId,
			Integer customerId, Integer amount) {
		
		super(orderId, createdAt);
		
		this.paymentId = paymentId;
		this.customerId = customerId;
		this.amount = amount;
	}

	private UUID paymentId;
	private Integer customerId;
	private Integer amount;
	
}
