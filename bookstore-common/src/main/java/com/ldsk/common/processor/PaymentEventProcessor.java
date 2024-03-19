package com.ldsk.common.processor;

import com.ldsk.common.events.DomainEvent;
import com.ldsk.common.events.payment.PaymentDeclinedEvent;
import com.ldsk.common.events.payment.PaymentDeductedEvent;
import com.ldsk.common.events.payment.PaymentEvent;
import com.ldsk.common.events.payment.PaymentRefundedEvent;

import reactor.core.publisher.Mono;

public interface PaymentEventProcessor<R extends DomainEvent> extends EventProcessor<PaymentEvent, R> {

	@Override
	default Mono<R> process(PaymentEvent event) {
		
		return selectEvent(event);
	}
	
	private Mono<R> selectEvent(PaymentEvent event) {
		
		if(event instanceof PaymentDeductedEvent paymentDeductedEvent) {
			
			return handlePaymentDeductedEvent(paymentDeductedEvent);
		} else if(event instanceof PaymentDeclinedEvent paymentDeclinedEvent) {
			
			return handlePaymentDeclinedEvent(paymentDeclinedEvent);
		} else if(event instanceof PaymentRefundedEvent paymentRefundedEvent) {
			
			return handlePaymentRefundedEvent(paymentRefundedEvent);
		}
		
		return null;
	}
	
    Mono<R> handlePaymentDeductedEvent(PaymentDeductedEvent event);

    Mono<R> handlePaymentDeclinedEvent(PaymentDeclinedEvent event);

    Mono<R> handlePaymentRefundedEvent(PaymentRefundedEvent event);	
	
}
