package com.ldsk.order.messaging.mapper;

import org.springframework.stereotype.Component;

import com.ldsk.common.events.shipping.ShippingScheduledEvent;
import com.ldsk.order.dto.OrderShipmentScheduleDTO;

@Component
public class ShippingEventMapper {

	public OrderShipmentScheduleDTO toOrderShipmentScheduleDTO(ShippingScheduledEvent shippingScheduledEvent) {
    	
    	return OrderShipmentScheduleDTO.builder()
	    			.orderId(shippingScheduledEvent.getOrderId())
	    			.deliveryDate(shippingScheduledEvent.getExpectedDelivery())
    			.build();
    }

}
