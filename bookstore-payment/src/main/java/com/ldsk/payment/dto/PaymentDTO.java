package com.ldsk.payment.dto;

import java.util.UUID;

import com.ldsk.common.events.payment.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {

	private UUID paymentId;
	private UUID orderId;
	private Integer customerId;
	private Integer amount;
	private PaymentStatus status;
	
}
