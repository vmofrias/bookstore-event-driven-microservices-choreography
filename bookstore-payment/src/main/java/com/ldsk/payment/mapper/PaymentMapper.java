package com.ldsk.payment.mapper;

import org.springframework.stereotype.Component;

import com.ldsk.payment.dto.PaymentDTO;
import com.ldsk.payment.dto.PaymentProcessRequestDTO;
import com.ldsk.payment.model.Payment;

@Component
public class PaymentMapper {

	public Payment toPayment(PaymentProcessRequestDTO paymentProcessRequestDTO) {
		
		return Payment.builder()
					.orderId(paymentProcessRequestDTO.getOrderId())
					.customerId(paymentProcessRequestDTO.getCustomerId())
					.amount(paymentProcessRequestDTO.getAmount())
				.build();
	}
	
	public PaymentDTO toPaymentDto(Payment payment) {
		
		return PaymentDTO.builder()
					.paymentId(payment.getPaymentId())
					.orderId(payment.getOrderId())
					.customerId(payment.getCustomerId())
					.amount(payment.getAmount())
					.status(payment.getStatus())
				.build();
	}
	
}
