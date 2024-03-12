package com.ldsk.common.processor;

import com.ldsk.common.events.OrderSaga;
import com.ldsk.common.events.inventory.InventoryDeclinedEvent;
import com.ldsk.common.events.inventory.InventoryDeductedEvent;
import com.ldsk.common.events.inventory.InventoryRestoredEvent;

import reactor.core.publisher.Mono;

public interface InventoryEventProcessor<R extends OrderSaga> extends EventProcessor<OrderSaga, R> {

	@Override
	default Mono<R> process(OrderSaga event) {
		
		return selectEvent(event);
	}
	
	private Mono<R> selectEvent(OrderSaga event) {
		
		if(event instanceof InventoryDeductedEvent) {
			
			return handleInventoryDeductedEvent(event);
		} else if(event instanceof InventoryDeclinedEvent) {
			
			return handleInventoryDeclinedEvent(event);
		} else if(event instanceof InventoryRestoredEvent) {
			
			return handleInventoryRestoredEvent(event);
		}
		
		return null;
	}
	
    Mono<R> handleInventoryDeductedEvent(OrderSaga event);

    Mono<R> handleInventoryDeclinedEvent(OrderSaga event);

    Mono<R> handleInventoryRestoredEvent(OrderSaga event);	
	
}
