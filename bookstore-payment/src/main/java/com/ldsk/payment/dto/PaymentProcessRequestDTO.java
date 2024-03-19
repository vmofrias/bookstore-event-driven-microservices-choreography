package com.ldsk.payment.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentProcessRequestDTO {

	private UUID orderId;
	private Integer customerId;
	private Integer amount;
	
}
