package com.ldsk.order.messaging.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldsk.common.events.inventory.InventoryDeclinedEvent;
import com.ldsk.common.events.inventory.InventoryDeductedEvent;
import com.ldsk.common.events.inventory.InventoryRestoredEvent;
import com.ldsk.common.events.order.OrderEvent;
import com.ldsk.common.processor.InventoryEventProcessor;
import com.ldsk.order.dto.OrderInventoryDTO;
import com.ldsk.order.messaging.mapper.InventoryEventMapper;
import com.ldsk.order.messaging.mapper.OrderEventMapper;
import com.ldsk.order.service.OrderFulfillmentService;
import com.ldsk.order.service.inventory.InventoryComponentStatusListener;

import reactor.core.publisher.Mono;

@Service
public class InventoryEventProcessorImpl implements InventoryEventProcessor<OrderEvent> {

	@Autowired
	private OrderFulfillmentService orderFulfillmentService;
	
	@Autowired
	private InventoryComponentStatusListener inventoryComponentStatusListener;
	
	@Autowired
	private InventoryEventMapper inventoryEventMapper;
	
	@Autowired
	private OrderEventMapper orderEventMapper;

	@Override
	public Mono<OrderEvent> handleInventoryDeductedEvent(InventoryDeductedEvent event) {
		
		OrderInventoryDTO orderInventoryDTO = inventoryEventMapper.toOrderInventoryDTO(event);
		
		return inventoryComponentStatusListener.onSuccess(orderInventoryDTO)
				.then(orderFulfillmentService.complete(event.getOrderId()))
				.map(orderEventMapper::toOrderCompletedEvent);
	}

	@Override
	public Mono<OrderEvent> handleInventoryDeclinedEvent(InventoryDeclinedEvent event) {
		
		OrderInventoryDTO orderInventoryDTO = inventoryEventMapper.toOrderInventoryDTO(event);
		
		return inventoryComponentStatusListener.onFailure(orderInventoryDTO)
				.then(orderFulfillmentService.cancel(event.getOrderId()))
				.map(orderEventMapper::toOrderCancelledEvent);
	}
	
	@Override
	public Mono<OrderEvent> handleInventoryRestoredEvent(InventoryRestoredEvent event) {
	
		OrderInventoryDTO orderInventoryDTO = inventoryEventMapper.toOrderInventoryDTO(event);
		
		return inventoryComponentStatusListener.onRollback(orderInventoryDTO)
				.then(Mono.empty());
	}
	
}
