package com.ldsk.order.messaging.mapper;

import java.time.Instant;

import org.springframework.stereotype.Component;

import com.ldsk.common.events.order.OrderCancelledEvent;
import com.ldsk.common.events.order.OrderCompletedEvent;
import com.ldsk.common.events.order.OrderCreatedEvent;
import com.ldsk.common.events.order.OrderEvent;
import com.ldsk.order.dto.PurchaseOrderDTO;

@Component
public class OrderEventMapper {

    public OrderEvent toOrderCreatedEvent(PurchaseOrderDTO purchaseOrderDTO) {
    	
    	return OrderCreatedEvent.builder()
	    			.orderId(purchaseOrderDTO.getOrderId())
	    			.unitPrice(purchaseOrderDTO.getUnitPrice())
	    			.quantity(purchaseOrderDTO.getQuantity())
	    			.productId(purchaseOrderDTO.getProductId())
	    			.totalAmount(purchaseOrderDTO.getAmount())
	    			.customerId(purchaseOrderDTO.getCustomerId())
	    			.createdAt(Instant.now())
    			.build();
    }

    public OrderEvent toOrderCancelledEvent(PurchaseOrderDTO purchaseOrderDTO) {
    	
    	return OrderCancelledEvent.builder()
	    			.orderId(purchaseOrderDTO.getOrderId())
	    			.createdAt(Instant.now())
    			.build();
    }

    public OrderEvent toOrderCompletedEvent(PurchaseOrderDTO purchaseOrderDTO) {
    	
    	return OrderCompletedEvent.builder()
	    			.orderId(purchaseOrderDTO.getOrderId())
	    			.createdAt(Instant.now())
    			.build();
    }

}
