package com.ldsk.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldsk.common.events.order.OrderStatus;
import com.ldsk.order.dto.OrderShipmentScheduleDTO;
import com.ldsk.order.repository.PurchaseOrderRepository;
import com.ldsk.order.service.shipping.ShippingComponentStatusListener;

import reactor.core.publisher.Mono;

@Service
public class ShippingComponentService implements ShippingComponentStatusListener {

	@Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Override
    public Mono<Void> onSuccess(OrderShipmentScheduleDTO orderShipmentScheduleDTO) {
    	
    	return purchaseOrderRepository.findByOrderIdAndStatus(orderShipmentScheduleDTO.getOrderId(), OrderStatus.COMPLETED)
    			.doOnNext(e -> e.setDeliveryDate(orderShipmentScheduleDTO.getDeliveryDate()))
    			.flatMap(purchaseOrderRepository::save)
    			.then();
    }

    @Override
    public Mono<Void> onFailure(OrderShipmentScheduleDTO orderShipmentScheduleDTO) {
    	
        return Mono.empty();
    }

    @Override
    public Mono<Void> onRollback(OrderShipmentScheduleDTO orderShipmentScheduleDTO) {
    	
        return Mono.empty();
    }

}
