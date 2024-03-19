package com.ldsk.inventory.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;

import com.ldsk.common.events.inventory.InventoryStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {

    @Id
    private UUID id;
    private UUID orderId;
    private Integer productId;
    private Integer quantity;
    private InventoryStatus status;
    
}
