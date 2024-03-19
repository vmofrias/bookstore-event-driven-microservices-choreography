package com.ldsk.order.messaging.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldsk.common.events.order.OrderEvent;
import com.ldsk.common.events.payment.PaymentDeclinedEvent;
import com.ldsk.common.events.payment.PaymentDeductedEvent;
import com.ldsk.common.events.payment.PaymentRefundedEvent;
import com.ldsk.common.processor.PaymentEventProcessor;
import com.ldsk.order.dto.OrderPaymentDTO;
import com.ldsk.order.messaging.mapper.OrderEventMapper;
import com.ldsk.order.messaging.mapper.PaymentEventMapper;
import com.ldsk.order.service.OrderFulfillmentService;
import com.ldsk.order.service.payment.PaymentComponentStatusListener;

import reactor.core.publisher.Mono;

@Service
public class PaymentEventProcessorImpl implements PaymentEventProcessor<OrderEvent> {

	@Autowired
	private OrderFulfillmentService orderFulfillmentService;
	
	@Autowired
	private PaymentComponentStatusListener paymentComponentStatusListener;
	
	@Autowired
	private PaymentEventMapper paymentEventMapper;
	
	@Autowired
	private OrderEventMapper orderEventMapper;

	@Override
	public Mono<OrderEvent> handlePaymentDeductedEvent(PaymentDeductedEvent event) {
		
		OrderPaymentDTO orderPaymentDTO = paymentEventMapper.toOrderPaymentDTO(event);
		
		return paymentComponentStatusListener.onSuccess(orderPaymentDTO)
				.then(orderFulfillmentService.complete(orderPaymentDTO.getOrderId()))
				.map(orderEventMapper::toOrderCompletedEvent);
	}
	
	@Override
	public Mono<OrderEvent> handlePaymentDeclinedEvent(PaymentDeclinedEvent event) {
		
		OrderPaymentDTO orderPaymentDTO = paymentEventMapper.toOrderPaymentDTO(event);
		
		return paymentComponentStatusListener.onFailure(orderPaymentDTO)
				.then(orderFulfillmentService.cancel(orderPaymentDTO.getOrderId()))
				.map(orderEventMapper::toOrderCancelledEvent);
	}
	
	@Override
	public Mono<OrderEvent> handlePaymentRefundedEvent(PaymentRefundedEvent event) {
		
		OrderPaymentDTO orderPaymentDTO = paymentEventMapper.toOrderPaymentDTO(event);
		
		return paymentComponentStatusListener.onRollback(orderPaymentDTO)
				.then(Mono.empty());
	}
	
}
