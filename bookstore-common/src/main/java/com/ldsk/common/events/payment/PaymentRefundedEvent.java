package com.ldsk.common.events.payment;

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
public class PaymentRefundedEvent implements PaymentEvent {
	
	private UUID orderId; 
	private Instant createdAt;
	private UUID paymentId;
	private Integer customerId;
	private Integer amount;
	
}
