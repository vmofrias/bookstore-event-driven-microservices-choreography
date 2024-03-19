package com.ldsk.order.dto;

import java.time.Instant;
import java.util.UUID;

import com.ldsk.common.events.order.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderDTO {

	private UUID orderId;
    private Integer customerId;
    private Integer productId;
    private Integer unitPrice;
    private Integer quantity;
    private Integer amount;
    private OrderStatus status;
    private Instant deliveryDate;
	
}
