package com.ldsk.common.processor;

import com.ldsk.common.events.DomainEvent;
import com.ldsk.common.events.order.OrderCancelledEvent;
import com.ldsk.common.events.order.OrderCompletedEvent;
import com.ldsk.common.events.order.OrderCreatedEvent;
import com.ldsk.common.events.order.OrderEvent;

import reactor.core.publisher.Mono;

public interface OrderEventProcessor<R extends DomainEvent> extends EventProcessor<OrderEvent, R> {

	@Override
	default Mono<R> process(OrderEvent event) {
		
		return selectEvent(event);
	}
	
	private Mono<R> selectEvent(OrderEvent event) {
		
		if(event instanceof OrderCreatedEvent orderCreatedEvent) {
			
			return handleOrderCreatedEvent(orderCreatedEvent);
		} else if(event instanceof OrderCancelledEvent orderCancelledEvent) {
			
			return handleOrderCancelledEvent(orderCancelledEvent);
		} else if(event instanceof OrderCompletedEvent orderCompletedEvent) {
			
			return handleOrderCompletedEvent(orderCompletedEvent);
		}
		
		return null;
	}
	
    Mono<R> handleOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent);

    Mono<R> handleOrderCancelledEvent(OrderCancelledEvent orderCancelledEvent);

    Mono<R> handleOrderCompletedEvent(OrderCompletedEvent orderCompletedEvent);	
	
}
