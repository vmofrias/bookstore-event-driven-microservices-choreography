package com.ldsk.common.processor;

import com.ldsk.common.events.DomainEvent;
import com.ldsk.common.events.inventory.InventoryDeclinedEvent;
import com.ldsk.common.events.inventory.InventoryDeductedEvent;
import com.ldsk.common.events.inventory.InventoryEvent;
import com.ldsk.common.events.inventory.InventoryRestoredEvent;

import reactor.core.publisher.Mono;

public interface InventoryEventProcessor<R extends DomainEvent> extends EventProcessor<InventoryEvent, R> {

	@Override
	default Mono<R> process(InventoryEvent event) {
		
		return selectEvent(event);
	}
	
	private Mono<R> selectEvent(InventoryEvent event) {
		
		if(event instanceof InventoryDeductedEvent inventoryDeductedEvent) {
			
			return handleInventoryDeductedEvent(inventoryDeductedEvent);
		} else if(event instanceof InventoryDeclinedEvent inventoryDeclinedEvent) {
			
			return handleInventoryDeclinedEvent(inventoryDeclinedEvent);
		} else if(event instanceof InventoryRestoredEvent inventoryRestoredEvent) {
			
			return handleInventoryRestoredEvent(inventoryRestoredEvent);
		}
		
		return null;
	}
	
    Mono<R> handleInventoryDeductedEvent(InventoryDeductedEvent event);

    Mono<R> handleInventoryDeclinedEvent(InventoryDeclinedEvent event);

    Mono<R> handleInventoryRestoredEvent(InventoryRestoredEvent event);	
	
}
