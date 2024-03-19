package com.ldsk.payment.messaging.mapper;

import java.time.Instant;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.ldsk.common.events.order.OrderCancelledEvent;
import com.ldsk.common.events.order.OrderCreatedEvent;
import com.ldsk.common.events.payment.PaymentDeclinedEvent;
import com.ldsk.common.events.payment.PaymentDeductedEvent;
import com.ldsk.common.events.payment.PaymentEvent;
import com.ldsk.common.events.payment.PaymentRefundedEvent;
import com.ldsk.payment.dto.PaymentDTO;
import com.ldsk.payment.dto.PaymentProcessRequestDTO;
import com.ldsk.payment.dto.RefundProcessRequestDTO;

import reactor.core.publisher.Mono;

@Component
public class MessageMapper {

	public PaymentProcessRequestDTO toPaymentProcessRequestDTO(OrderCreatedEvent orderCreatedEvent) {
		
		return PaymentProcessRequestDTO.builder()
					.customerId(orderCreatedEvent.getCustomerId())
					.orderId(orderCreatedEvent.getOrderId())
					.amount(orderCreatedEvent.getTotalAmount())
				.build();
	}
	
	public RefundProcessRequestDTO toRefundProcessRequestDTO(OrderCancelledEvent orderCancelledEvent) {
		
		return RefundProcessRequestDTO.builder()
					.orderId(orderCancelledEvent.getOrderId())
				.build();
	}
	
	public PaymentEvent toPaymentDeductedEvent(PaymentDTO paymentDTO) {
		
		return PaymentDeductedEvent.builder()
					.paymentId(paymentDTO.getPaymentId())
					.orderId(paymentDTO.getOrderId())
					.amount(paymentDTO.getAmount())
					.customerId(paymentDTO.getCustomerId())
					.createdAt(Instant.now())
				.build();
	}
	
    public PaymentEvent toPaymentRefundedEvent(PaymentDTO paymentDTO) {
    	
    	return PaymentRefundedEvent.builder()
	    			.paymentId(paymentDTO.getPaymentId())
	    			.orderId(paymentDTO.getOrderId())
	    			.amount(paymentDTO.getAmount())
	    			.customerId(paymentDTO.getCustomerId())
	    			.createdAt(Instant.now())
    			.build();
    }
    
    public Function<Throwable, Mono<PaymentEvent>> toPaymentDeclinedEvent(OrderCreatedEvent orderCreatedEvent) {
        
    	return ex -> Mono.fromSupplier(() -> PaymentDeclinedEvent.builder()
	    			.orderId(orderCreatedEvent.getOrderId())
	    			.amount(orderCreatedEvent.getTotalAmount())
	    			.customerId(orderCreatedEvent.getCustomerId())
	    			.createdAt(Instant.now())
	    			.message(ex.getMessage())
    			.build());
    }
}