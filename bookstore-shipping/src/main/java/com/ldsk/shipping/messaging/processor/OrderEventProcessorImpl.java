package com.ldsk.shipping.messaging.processor;

import java.util.function.UnaryOperator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldsk.common.events.exception.EventAlreadyProcessedException;
import com.ldsk.common.events.order.OrderCancelledEvent;
import com.ldsk.common.events.order.OrderCompletedEvent;
import com.ldsk.common.events.order.OrderCreatedEvent;
import com.ldsk.common.events.shipping.ShippingEvent;
import com.ldsk.common.processor.OrderEventProcessor;
import com.ldsk.shipping.messaging.mapper.MessageMapper;
import com.ldsk.shipping.service.ShippingServiceImpl;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Service
@Log4j2
public class OrderEventProcessorImpl implements OrderEventProcessor<ShippingEvent> {

	@Autowired
	private ShippingServiceImpl shippingServiceImpl;
	
	@Autowired
	private MessageMapper messageMapper;
	
	@Override
	public Mono<ShippingEvent> handleOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent) {
		
		return shippingServiceImpl.addShipment(messageMapper.toScheduleRequestDTO(orderCreatedEvent))
				.transform(exceptionHandler())
				.then(Mono.empty());
	}

	@Override
	public Mono<ShippingEvent> handleOrderCancelledEvent(OrderCancelledEvent orderCancelledEvent) {
		
		return shippingServiceImpl.cancel(orderCancelledEvent.getOrderId())
				.then(Mono.empty());
	}

	@Override
	public Mono<ShippingEvent> handleOrderCompletedEvent(OrderCompletedEvent orderCompletedEvent) {
		
		return shippingServiceImpl.schedule(orderCompletedEvent.getOrderId())
				.map(messageMapper::toShippingScheduledEvent)
				.doOnNext(e -> log.info("Shipping scheduled {}", e));
	}
	
    private <T> UnaryOperator<Mono<T>> exceptionHandler() {
        return mono -> mono.onErrorResume(EventAlreadyProcessedException.class, ex -> Mono.empty())
                           .doOnError(ex -> log.error(ex.getMessage()));
    }
	
}
