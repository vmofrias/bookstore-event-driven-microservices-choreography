package com.ldsk.order.messaging.publisher;

import java.time.Duration;

import com.ldsk.common.events.order.OrderEvent;
import com.ldsk.common.publisher.EventPublisher;
import com.ldsk.order.dto.PurchaseOrderDTO;
import com.ldsk.order.messaging.mapper.OrderEventMapper;
import com.ldsk.order.service.OrderEventListener;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@RequiredArgsConstructor
public class OrderEventListenerImpl implements OrderEventListener, EventPublisher<OrderEvent> {

	
	private final Sinks.Many<OrderEvent> sink;
	private final Flux<OrderEvent> flux;
	
	public Flux<OrderEvent> publish() {
		
		return this.flux;
	}

	@Override
	public void emitOrderCreated(PurchaseOrderDTO purchaseOrderDTO) {
		
		OrderEventMapper orderEventMapper = new OrderEventMapper();
		
		OrderEvent orderCreatedEvent = orderEventMapper.toOrderCreatedEvent(purchaseOrderDTO);
		
		sink.emitNext(orderCreatedEvent, Sinks.EmitFailureHandler.busyLooping(Duration.ofSeconds(1)));
	}
	
}
