package com.ldsk.common.processor;

import com.ldsk.common.events.OrderSaga;
import com.ldsk.common.events.shipping.ShippingScheduledEvent;

import reactor.core.publisher.Mono;

public interface ShippingEventProcessor<R extends OrderSaga> extends EventProcessor<OrderSaga, R> {

	@Override
	default Mono<R> process(OrderSaga event) {
		
		return selectEvent(event);
	}
	
	private Mono<R> selectEvent(OrderSaga event) {
		
		if(event instanceof ShippingScheduledEvent) {
			
			return handleShippingScheduledEvent(event);
		} 
		
		return null;
	}
	
    Mono<R> handleShippingScheduledEvent(OrderSaga event);

}
