package com.ldsk.common.publisher;

import com.ldsk.common.events.DomainEvent;

import reactor.core.publisher.Flux;

public interface EventPublisher<T extends DomainEvent> {

	Flux<T> publish();
	
}
