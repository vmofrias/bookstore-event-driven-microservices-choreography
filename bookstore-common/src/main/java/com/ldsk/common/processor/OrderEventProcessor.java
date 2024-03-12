package com.ldsk.common.processor;

import com.ldsk.common.events.OrderSaga;
import com.ldsk.common.events.order.OrderCancelledEvent;
import com.ldsk.common.events.order.OrderCompletedEvent;
import com.ldsk.common.events.order.OrderCreatedEvent;

import reactor.core.publisher.Mono;

public interface OrderEventProcessor<R extends OrderSaga> extends EventProcessor<OrderSaga, R> {

	@Override
	default Mono<R> process(OrderSaga event) {
		
		return selectEvent(event);
	}
	
	private Mono<R> selectEvent(OrderSaga event) {
		
		if(event instanceof OrderCreatedEvent) {
			
			return handleOrderCreatedEvent(event);
		} else if(event instanceof OrderCancelledEvent) {
			
			return handleOrderCancelledEvent(event);
		} else if(event instanceof OrderCompletedEvent) {
			
			return handleOrderCompletedEvent(event);
		}
		
		return null;
	}
	
    Mono<R> handleOrderCreatedEvent(OrderSaga event);

    Mono<R> handleOrderCancelledEvent(OrderSaga event);

    Mono<R> handleOrderCompletedEvent(OrderSaga event);	
	
}
