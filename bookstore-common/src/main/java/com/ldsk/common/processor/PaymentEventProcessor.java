package com.ldsk.common.processor;

import com.ldsk.common.events.OrderSaga;
import com.ldsk.common.events.payment.PaymentDeclinedEvent;
import com.ldsk.common.events.payment.PaymentDeductedEvent;
import com.ldsk.common.events.payment.PaymentRefundedEvent;

import reactor.core.publisher.Mono;

public interface PaymentEventProcessor<R extends OrderSaga> extends EventProcessor<OrderSaga, R> {

	@Override
	default Mono<R> process(OrderSaga event) {
		
		return selectEvent(event);
	}
	
	private Mono<R> selectEvent(OrderSaga event) {
		
		if(event instanceof PaymentDeductedEvent) {
			
			return handlePaymentDeductedEvent(event);
		} else if(event instanceof PaymentDeclinedEvent) {
			
			return handlePaymentDeclinedEvent(event);
		} else if(event instanceof PaymentRefundedEvent) {
			
			return handlePaymentRefundedEvent(event);
		}
		
		return null;
	}
	
    Mono<R> handlePaymentDeductedEvent(OrderSaga event);

    Mono<R> handlePaymentDeclinedEvent(OrderSaga event);

    Mono<R> handlePaymentRefundedEvent(OrderSaga event);	
	
}
