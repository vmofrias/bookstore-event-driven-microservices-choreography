package com.ldsk.payment.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;

import com.ldsk.common.events.payment.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

	@Id
	private UUID paymentId;
	private UUID orderId;
	private Integer customerId;
	private PaymentStatus status;
	private Integer amount;
	
}
