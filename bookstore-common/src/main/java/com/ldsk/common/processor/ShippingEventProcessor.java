package com.ldsk.common.processor;

import com.ldsk.common.events.DomainEvent;
import com.ldsk.common.events.shipping.ShippingEvent;
import com.ldsk.common.events.shipping.ShippingScheduledEvent;

import reactor.core.publisher.Mono;

public interface ShippingEventProcessor<R extends DomainEvent> extends EventProcessor<ShippingEvent, R> {

	@Override
	default Mono<R> process(ShippingEvent event) {
		
		return selectEvent(event);
	}
	
	private Mono<R> selectEvent(ShippingEvent event) {
		
		if(event instanceof ShippingScheduledEvent shippingScheduledEvent) {
			
			return handleShippingScheduledEvent(shippingScheduledEvent);
		} 
		
		return null;
	}
	
    Mono<R> handleShippingScheduledEvent(ShippingScheduledEvent event);

}
