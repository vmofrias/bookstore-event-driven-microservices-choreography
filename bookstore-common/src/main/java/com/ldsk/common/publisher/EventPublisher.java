package com.ldsk.common.publisher;

import com.ldsk.common.events.OrderSaga;

import reactor.core.publisher.Flux;

public interface EventPublisher<T extends OrderSaga> {

	Flux<T> publish();
	
}
