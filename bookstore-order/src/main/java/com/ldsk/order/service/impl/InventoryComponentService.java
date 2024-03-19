package com.ldsk.order.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldsk.order.dto.OrderInventoryDTO;
import com.ldsk.order.mapper.OrderMapper;
import com.ldsk.order.model.OrderInventory;
import com.ldsk.order.repository.OrderInventoryRepository;
import com.ldsk.order.service.inventory.InventoryComponentFetcher;
import com.ldsk.order.service.inventory.InventoryComponentStatusListener;

import reactor.core.publisher.Mono;

@Service
public class InventoryComponentService implements InventoryComponentFetcher, InventoryComponentStatusListener {

    private static final OrderInventoryDTO DEFAULT = OrderInventoryDTO.builder().build();
    
    @Autowired
    private OrderInventoryRepository orderInventoryRepository;
   
    @Autowired
    private OrderMapper orderMapper;
    
    
    @Override
    public Mono<OrderInventoryDTO> getComponent(UUID orderId) {
        
    	return orderInventoryRepository.findByOrderId(orderId)
    			.map(orderMapper::toOrderInventoryDTO)
    			.defaultIfEmpty(DEFAULT);
    }

    @Override
    public Mono<Void> onSuccess(OrderInventoryDTO orderInventoryDTO) {
    	
    	return orderInventoryRepository.findByOrderId(orderInventoryDTO.getOrderId())
    			.switchIfEmpty(Mono.defer(() -> this.add(orderInventoryDTO, true)))
    			.then();
    }

    @Override
    public Mono<Void> onFailure(OrderInventoryDTO orderInventoryDTO) {
    	
    	return orderInventoryRepository.findByOrderId(orderInventoryDTO.getOrderId())
    			.switchIfEmpty(Mono.defer(() -> this.add(orderInventoryDTO, false)))
    			.then();
    }

    @Override
    public Mono<Void> onRollback(OrderInventoryDTO orderInventoryDTO) {
    	
    	return orderInventoryRepository.findByOrderId(orderInventoryDTO.getOrderId())
    			.doOnNext(e -> e.setStatus(orderInventoryDTO.getStatus()))
    			.flatMap(orderInventoryRepository::save)
    			.then();
    }

    private Mono<OrderInventory> add(OrderInventoryDTO orderInventoryDTO, boolean isSuccess) {
    	
    	OrderInventory orderInventory = orderMapper.toOrderInventory(orderInventoryDTO);
    	orderInventory.setSuccess(isSuccess);

    	return orderInventoryRepository.save(orderInventory);
    }

}
