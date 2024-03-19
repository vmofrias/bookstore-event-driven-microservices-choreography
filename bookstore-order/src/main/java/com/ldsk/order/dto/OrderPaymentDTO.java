package com.ldsk.order.dto;

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
public class OrderPaymentDTO {

	private UUID orderId;
    private UUID paymentId;
    private PaymentStatus status;
    private String message;
	
}
