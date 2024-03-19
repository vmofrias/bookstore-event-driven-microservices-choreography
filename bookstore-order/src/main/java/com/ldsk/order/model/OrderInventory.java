package com.ldsk.order.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;

import com.ldsk.common.events.inventory.InventoryStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderInventory {

    @Id
    private Integer id;
    private UUID orderId;
    private UUID inventoryId;
    private InventoryStatus status;
    private String message;
    private Boolean success;
	
}
