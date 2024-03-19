package com.ldsk.order.service.impl;

import java.util.UUID;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.ldsk.common.events.order.OrderStatus;
import com.ldsk.order.dto.PurchaseOrderDTO;
import com.ldsk.order.mapper.OrderMapper;
import com.ldsk.order.model.PurchaseOrder;
import com.ldsk.order.repository.PurchaseOrderRepository;
import com.ldsk.order.service.OrderFulfillmentService;

import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Service
public class OrderFulfillmentServiceImpl implements OrderFulfillmentService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;
    
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Mono<PurchaseOrderDTO> complete(UUID orderId) {
    	
    	return purchaseOrderRepository.getWhenOrderComponentsCompleted(orderId)
    			.transform(this.updateStatus(OrderStatus.COMPLETED));
    }

    @Override
    public Mono<PurchaseOrderDTO> cancel(UUID orderId) {
        
    	return purchaseOrderRepository.findByOrderIdAndStatus(orderId, OrderStatus.PENDING)
    			.transform(this.updateStatus(OrderStatus.CANCELLED));
    }

    private Function<Mono<PurchaseOrder>, Mono<PurchaseOrderDTO>> updateStatus(OrderStatus orderStatus) {
    	
    	return mono -> mono
    			.doOnNext(e -> e.setStatus(orderStatus))
    			.flatMap(purchaseOrderRepository::save)
    			.retryWhen(Retry.max(1).filter(OptimisticLockingFailureException.class::isInstance))
    			.map(orderMapper::toPurchaseOrderDTO);
    }

}
