package com.ldsk.common.processor;

import com.ldsk.common.events.OrderSaga;

import reactor.core.publisher.Mono;

public interface EventProcessor<T extends OrderSaga, R extends OrderSaga> {

	Mono<R> process(T event);
	
}
