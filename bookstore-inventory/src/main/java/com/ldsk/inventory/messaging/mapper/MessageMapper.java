package com.ldsk.inventory.messaging.mapper;

import java.time.Instant;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.ldsk.common.events.inventory.InventoryDeclinedEvent;
import com.ldsk.common.events.inventory.InventoryDeductedEvent;
import com.ldsk.common.events.inventory.InventoryEvent;
import com.ldsk.common.events.inventory.InventoryRestoredEvent;
import com.ldsk.common.events.order.OrderCreatedEvent;
import com.ldsk.inventory.dto.InventoryDeductRequestDTO;
import com.ldsk.inventory.dto.OrderInventoryDTO;

import reactor.core.publisher.Mono;

@Component
public class MessageMapper {

	
    public InventoryDeductRequestDTO toInventoryDeductRequestDTO(OrderCreatedEvent orderCreatedEvent) {
    	
    	return InventoryDeductRequestDTO.builder()
	    			.orderId(orderCreatedEvent.getOrderId())
	    			.productId(orderCreatedEvent.getProductId())
	    			.quantity(orderCreatedEvent.getQuantity())
    			.build();
    }

    public InventoryEvent toInventoryDeductedEvent(OrderInventoryDTO orderInventoryDTO) {
    	
    	return InventoryDeductedEvent.builder()
	    			.inventoryId(orderInventoryDTO.getInventoryId())
	    			.orderId(orderInventoryDTO.getOrderId())
	    			.productId(orderInventoryDTO.getProductId())
	    			.quantity(orderInventoryDTO.getQuantity())
	    			.createdAt(Instant.now())
    			.build();
    }

    public InventoryEvent toInventoryRestoredEvent(OrderInventoryDTO orderInventoryDTO) {
    	
    	return InventoryRestoredEvent.builder()
	    			.orderId(orderInventoryDTO.getOrderId())
	    			.inventoryId(orderInventoryDTO.getInventoryId())
	    			.productId(orderInventoryDTO.getProductId())
	    			.quantity(orderInventoryDTO.getQuantity())
	    			.createdAt(Instant.now())
    			.build();
    }

    public Function<Throwable, Mono<InventoryEvent>> toInventoryDeclinedEvent(OrderCreatedEvent orderCreatedEvent) {
    	
    	return ex -> Mono.fromSupplier(() -> InventoryDeclinedEvent.builder()
	    			.orderId(orderCreatedEvent.getOrderId())
	    			.productId(orderCreatedEvent.getProductId())
	    			.quantity(orderCreatedEvent.getQuantity())
	    			.createdAt(Instant.now())
	    			.message(ex.getMessage())
    			.build());
    }
    
}
