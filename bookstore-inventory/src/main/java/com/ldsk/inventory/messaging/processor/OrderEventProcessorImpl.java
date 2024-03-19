package com.ldsk.inventory.messaging.processor;

import java.util.function.UnaryOperator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldsk.common.events.exception.EventAlreadyProcessedException;
import com.ldsk.common.events.inventory.InventoryEvent;
import com.ldsk.common.events.order.OrderCancelledEvent;
import com.ldsk.common.events.order.OrderCompletedEvent;
import com.ldsk.common.events.order.OrderCreatedEvent;
import com.ldsk.common.processor.OrderEventProcessor;
import com.ldsk.inventory.messaging.mapper.MessageMapper;
import com.ldsk.inventory.service.InventoryServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Service
@Log4j2
@RequiredArgsConstructor
public class OrderEventProcessorImpl implements OrderEventProcessor<InventoryEvent> {

	@Autowired
	private InventoryServiceImpl inventoryServiceImpl;
	
	@Autowired
	private MessageMapper messageMapper;
	
	@Override
	public Mono<InventoryEvent> handleOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent) {
		
		return inventoryServiceImpl.deduct(messageMapper.toInventoryDeductRequestDTO(orderCreatedEvent))
				.map(messageMapper::toInventoryDeductedEvent)
				.doOnNext(e -> log.info("Inventory deducted {}", e))
				.transform(exceptionHandler(orderCreatedEvent));
	}
	
	@Override
	public Mono<InventoryEvent> handleOrderCancelledEvent(OrderCancelledEvent orderCancelledEvent) {
		
		return inventoryServiceImpl.restore(orderCancelledEvent.getOrderId())
				.map(messageMapper::toInventoryRestoredEvent)
				.doOnNext(e -> log.info("Inventory restored {}", e))
				.doOnError(ex -> log.error("Error while processing restore.", ex));
	}
	
	@Override
	public Mono<InventoryEvent> handleOrderCompletedEvent(OrderCompletedEvent orderCompletedEvent) {
		
		return Mono.empty();
	}
	
    private UnaryOperator<Mono<InventoryEvent>> exceptionHandler(OrderCreatedEvent orderCreatedEvent) {
    	
        return mono -> mono.onErrorResume(EventAlreadyProcessedException.class, e -> Mono.empty())
                           .onErrorResume(messageMapper.toInventoryDeclinedEvent(orderCreatedEvent));
    }
    
}
