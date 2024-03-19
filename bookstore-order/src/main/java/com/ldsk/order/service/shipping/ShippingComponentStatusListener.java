package com.ldsk.order.service.shipping;

import com.ldsk.order.dto.OrderShipmentScheduleDTO;
import com.ldsk.order.service.OrderComponentStatusListener;

public interface ShippingComponentStatusListener extends OrderComponentStatusListener<OrderShipmentScheduleDTO> {
	
}
