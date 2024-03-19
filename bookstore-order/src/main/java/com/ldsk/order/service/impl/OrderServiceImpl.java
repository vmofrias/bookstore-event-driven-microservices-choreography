package com.ldsk.order.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldsk.order.dto.OrderCreateRequestDTO;
import com.ldsk.order.dto.OrderDetailsDTO;
import com.ldsk.order.dto.PurchaseOrderDTO;
import com.ldsk.order.mapper.OrderMapper;
import com.ldsk.order.model.PurchaseOrder;
import com.ldsk.order.repository.PurchaseOrderRepository;
import com.ldsk.order.service.OrderEventListener;
import com.ldsk.order.service.OrderService;
import com.ldsk.order.service.inventory.InventoryComponentFetcher;
import com.ldsk.order.service.payment.PaymentComponentFetcher;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private PurchaseOrderRepository purchaseOrderRepository;

	@Autowired
    private OrderEventListener orderEventListener;
    
	@Autowired
	private PaymentComponentFetcher paymentComponentFetcher;

	@Autowired
	private InventoryComponentFetcher inventoryComponentFetcher;
	
	@Autowired
	private OrderMapper orderMapper;
	
    @Override
    public Mono<PurchaseOrderDTO> placeOrder(OrderCreateRequestDTO orderCreateRequestDTO) {
    	
    	PurchaseOrder purchaseOrder = orderMapper.toPurchaseOrder(orderCreateRequestDTO);
    	
    	return purchaseOrderRepository.save(purchaseOrder)
    			.map(orderMapper::toPurchaseOrderDTO)
    			.doOnNext(orderEventListener::emitOrderCreated);
    }

    @Override
    public Flux<PurchaseOrderDTO> getAllOrders() {
    	
    	return purchaseOrderRepository.findAll()
    			.map(orderMapper::toPurchaseOrderDTO);
    }

    @Override
    public Mono<OrderDetailsDTO> getOrderDetails(UUID orderId) {
    	
    	return purchaseOrderRepository.findById(orderId)
    			.map(orderMapper::toPurchaseOrderDTO)
    			.flatMap(dto -> paymentComponentFetcher.getComponent(orderId)
    					.zipWith(inventoryComponentFetcher.getComponent(orderId))
    					.map(t -> orderMapper.toOrderDetailsDTO(dto, t.getT1(), t.getT2())));
    }

}
