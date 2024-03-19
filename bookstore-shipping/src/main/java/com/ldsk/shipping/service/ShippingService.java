package com.ldsk.shipping.service;

import java.util.UUID;

import com.ldsk.shipping.dto.ScheduleRequestDTO;
import com.ldsk.shipping.dto.ShipmentDTO;

import reactor.core.publisher.Mono;

public interface ShippingService {

    Mono<Void> addShipment(ScheduleRequestDTO scheduleRequestDTO);

    Mono<Void> cancel(UUID orderId);

    Mono<ShipmentDTO> schedule(UUID orderId);
	
}
