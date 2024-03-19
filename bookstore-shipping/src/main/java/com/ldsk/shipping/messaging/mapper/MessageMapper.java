package com.ldsk.shipping.messaging.mapper;

import java.time.Instant;

import org.springframework.stereotype.Component;

import com.ldsk.common.events.order.OrderCreatedEvent;
import com.ldsk.common.events.shipping.ShippingEvent;
import com.ldsk.common.events.shipping.ShippingScheduledEvent;
import com.ldsk.shipping.dto.ScheduleRequestDTO;
import com.ldsk.shipping.dto.ShipmentDTO;

@Component
public class MessageMapper {

    public ScheduleRequestDTO toScheduleRequestDTO(OrderCreatedEvent orderCreatedEvent) {
    	
    	return ScheduleRequestDTO.builder()
	    			.customerId(orderCreatedEvent.getCustomerId())
	    			.productId(orderCreatedEvent.getProductId())
	    			.quantity(orderCreatedEvent.getQuantity())
	    			.orderId(orderCreatedEvent.getOrderId())
    			.build();
    }

    public ShippingEvent toShippingScheduledEvent(ShipmentDTO shipmentDTO) {
    	
    	return ShippingScheduledEvent.builder()
	    			.shipmentId(shipmentDTO.getShipmentId())
	    			.orderId(shipmentDTO.getOrderId())
	    			.createdAt(Instant.now())
	    			.expectedDelivery(shipmentDTO.getDeliveryDate())
    			.build();
    }
	
}
