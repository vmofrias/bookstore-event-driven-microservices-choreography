package com.ldsk.order.messaging.mapper;

import org.springframework.stereotype.Component;

import com.ldsk.common.events.inventory.InventoryDeclinedEvent;
import com.ldsk.common.events.inventory.InventoryDeductedEvent;
import com.ldsk.common.events.inventory.InventoryRestoredEvent;
import com.ldsk.common.events.inventory.InventoryStatus;
import com.ldsk.order.dto.OrderInventoryDTO;

@Component
public class InventoryEventMapper {

    public OrderInventoryDTO toOrderInventoryDTO(InventoryDeductedEvent inventoryDeductedEvent) {
    	
    	return OrderInventoryDTO.builder()
	    			.orderId(inventoryDeductedEvent.getOrderId())
	    			.inventoryId(inventoryDeductedEvent.getInventoryId())
	    			.status(InventoryStatus.DEDUCTED)
    			.build();
    }

    public OrderInventoryDTO toOrderInventoryDTO(InventoryDeclinedEvent inventoryDeclinedEvent) {
    	
    	return OrderInventoryDTO.builder()
	    			.orderId(inventoryDeclinedEvent.getOrderId())
	    			.status(InventoryStatus.DECLINED)
	    			.message(inventoryDeclinedEvent.getMessage())
    			.build();
    }

    public OrderInventoryDTO toOrderInventoryDTO(InventoryRestoredEvent inventoryRestoredEvent) {
    	
    	return OrderInventoryDTO.builder()
	    			.orderId(inventoryRestoredEvent.getOrderId())
	    			.status(InventoryStatus.RESTORED)
    			.build();
    }
	
}
