package com.ldsk.inventory.mapper;

import org.springframework.stereotype.Component;

import com.ldsk.inventory.dto.InventoryDeductRequestDTO;
import com.ldsk.inventory.dto.OrderInventoryDTO;
import com.ldsk.inventory.model.Inventory;

@Component
public class InventoryMapper {

    public Inventory toInventory(InventoryDeductRequestDTO inventoryDeductRequestDTO) {
    	
    	return Inventory.builder()
	    			.orderId(inventoryDeductRequestDTO.getOrderId())
	    			.productId(inventoryDeductRequestDTO.getProductId())
	    			.quantity(inventoryDeductRequestDTO.getQuantity())
    			.build();
    }

    public OrderInventoryDTO toOrderInventoryDTO(Inventory inventory) {
    	
    	return OrderInventoryDTO.builder()
	    			.inventoryId(inventory.getId())
	    			.orderId(inventory.getOrderId())
	    			.productId(inventory.getProductId())
	    			.quantity(inventory.getQuantity())
	    			.status(inventory.getStatus())
    			.build();
    }
	
}
