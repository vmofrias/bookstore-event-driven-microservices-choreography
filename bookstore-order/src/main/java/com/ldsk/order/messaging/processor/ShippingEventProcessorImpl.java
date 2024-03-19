package com.ldsk.order.messaging.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldsk.common.events.order.OrderEvent;
import com.ldsk.common.events.shipping.ShippingScheduledEvent;
import com.ldsk.common.processor.ShippingEventProcessor;
import com.ldsk.order.dto.OrderShipmentScheduleDTO;
import com.ldsk.order.messaging.mapper.ShippingEventMapper;
import com.ldsk.order.service.shipping.ShippingComponentStatusListener;

import reactor.core.publisher.Mono;

@Service
public class ShippingEventProcessorImpl implements ShippingEventProcessor<OrderEvent> {

	@Autowired
	private ShippingComponentStatusListener shippingComponentStatusListener;
	
	@Autowired
	private ShippingEventMapper shippingEventMapper;
    
	@Override
	public Mono<OrderEvent> handleShippingScheduledEvent(ShippingScheduledEvent event) {
		
		OrderShipmentScheduleDTO orderShipmentScheduleDTO = shippingEventMapper.toOrderShipmentScheduleDTO(event);
		
		return shippingComponentStatusListener.onSuccess(orderShipmentScheduleDTO)
				.then(Mono.empty());
	}

}
