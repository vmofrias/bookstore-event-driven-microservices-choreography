package com.ldsk.order.service;

import java.util.UUID;

import reactor.core.publisher.Mono;

public interface OrderComponentFetcher<T> {

	Mono<T> getComponent(UUID orderId);
	
}
