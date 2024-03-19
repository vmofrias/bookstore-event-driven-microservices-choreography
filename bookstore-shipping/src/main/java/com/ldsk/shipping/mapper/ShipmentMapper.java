package com.ldsk.shipping.mapper;

import org.springframework.stereotype.Component;

import com.ldsk.shipping.dto.ScheduleRequestDTO;
import com.ldsk.shipping.dto.ShipmentDTO;
import com.ldsk.shipping.model.Shipment;

@Component
public class ShipmentMapper {

    public Shipment toShipment(ScheduleRequestDTO scheduleRequestDTO) {
    	
    	return Shipment.builder()
	    			.customerId(scheduleRequestDTO.getCustomerId())
	    			.orderId(scheduleRequestDTO.getOrderId())
	    			.productId(scheduleRequestDTO.getProductId())
	    			.quantity(scheduleRequestDTO.getQuantity())
    			.build();
    }

    public ShipmentDTO toShipmentDTO(Shipment shipment) {
    	
    	return ShipmentDTO.builder()
	    			.shipmentId(shipment.getId())
	    			.customerId(shipment.getCustomerId())
	    			.quantity(shipment.getQuantity())
	    			.productId(shipment.getProductId())
	    			.orderId(shipment.getOrderId())
	    			.status(shipment.getStatus())
	    			.deliveryDate(shipment.getDeliveryDate())
    			.build();
    }
	
}
