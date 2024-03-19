package com.ldsk.shipping.service;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldsk.common.events.shipping.ShippingStatus;
import com.ldsk.common.events.util.DuplicateEventValidator;
import com.ldsk.shipping.dto.ScheduleRequestDTO;
import com.ldsk.shipping.dto.ShipmentDTO;
import com.ldsk.shipping.mapper.ShipmentMapper;
import com.ldsk.shipping.model.Shipment;
import com.ldsk.shipping.repository.ShipmentRepository;

import reactor.core.publisher.Mono;

@Service
public class ShippingServiceImpl implements ShippingService {
	
	@Autowired
	private ShipmentRepository shipmentRepository;
	
	@Autowired
	private ShipmentMapper shipmentMapper;

	@Override
	public Mono<Void> addShipment(ScheduleRequestDTO scheduleRequestDTO) {

		return DuplicateEventValidator.validate(shipmentRepository.existsByOrderId(
				scheduleRequestDTO.getOrderId()), 
				Mono.defer(() -> add(scheduleRequestDTO)));
	}
	
	private Mono<Void> add(ScheduleRequestDTO scheduleRequestDTO) {
		
		Shipment shipment = shipmentMapper.toShipment(scheduleRequestDTO);
		shipment.setStatus(ShippingStatus.PENDING);

		return shipmentRepository.save(shipment).then();
	}

	@Override
	public Mono<Void> cancel(UUID orderId) {

		return shipmentRepository.deleteByOrderId(orderId);
	}

	@Override
	public Mono<ShipmentDTO> schedule(UUID orderId) {

		return shipmentRepository.findByOrderIdAndStatus(orderId, ShippingStatus.PENDING)
				.flatMap(this::schedule);
	}
	
	private Mono<ShipmentDTO> schedule(Shipment shipment) {
		
		shipment.setDeliveryDate(Instant.now().plus(Duration.ofDays(3)));
		shipment.setStatus(ShippingStatus.SCHEDULED);
		
		return shipmentRepository.save(shipment)
				.map(shipmentMapper::toShipmentDTO);
	}

}
