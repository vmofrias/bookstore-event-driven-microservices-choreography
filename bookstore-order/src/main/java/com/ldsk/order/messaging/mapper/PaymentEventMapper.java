package com.ldsk.order.messaging.mapper;

import org.springframework.stereotype.Component;

import com.ldsk.common.events.payment.PaymentDeclinedEvent;
import com.ldsk.common.events.payment.PaymentDeductedEvent;
import com.ldsk.common.events.payment.PaymentRefundedEvent;
import com.ldsk.common.events.payment.PaymentStatus;
import com.ldsk.order.dto.OrderPaymentDTO;

@Component
public class PaymentEventMapper {

    public OrderPaymentDTO toOrderPaymentDTO(PaymentDeductedEvent paymentDeductedEvent) {
    	
    	return OrderPaymentDTO.builder()
	    			.orderId(paymentDeductedEvent.getOrderId())
	    			.paymentId(paymentDeductedEvent.getPaymentId())
	    			.status(PaymentStatus.DEDUCTED)
    			.build();
    }

    public OrderPaymentDTO toOrderPaymentDTO(PaymentDeclinedEvent paymentDeclinedEvent) {
    	
    	return OrderPaymentDTO.builder()
	    			.orderId(paymentDeclinedEvent.getOrderId())
	    			.status(PaymentStatus.DECLINED)
	    			.message(paymentDeclinedEvent.getMessage())
    			.build();
    }
    
    public OrderPaymentDTO toOrderPaymentDTO(PaymentRefundedEvent paymentRefundedEvent) {
    	
    	return OrderPaymentDTO.builder()
	    			.orderId(paymentRefundedEvent.getOrderId())
	    			.status(PaymentStatus.REFUNDED)
    			.build();
    }

}
