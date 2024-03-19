package com.ldsk.order.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldsk.order.dto.OrderPaymentDTO;
import com.ldsk.order.mapper.OrderMapper;
import com.ldsk.order.model.OrderPayment;
import com.ldsk.order.repository.OrderPaymentRepository;
import com.ldsk.order.service.payment.PaymentComponentFetcher;
import com.ldsk.order.service.payment.PaymentComponentStatusListener;

import reactor.core.publisher.Mono;

@Service
public class PaymentComponentService implements PaymentComponentFetcher, PaymentComponentStatusListener {

    private static final OrderPaymentDTO DEFAULT = OrderPaymentDTO.builder().build();
    
    @Autowired
    private OrderPaymentRepository orderPaymentRepository;

    @Autowired
    private OrderMapper orderMapper;
    
    @Override
    public Mono<OrderPaymentDTO> getComponent(UUID orderId) {
    	
    	return orderPaymentRepository.findByOrderId(orderId)
    			.map(orderMapper::toOrderPaymentDTO)
    			.defaultIfEmpty(DEFAULT);
    }

    @Override
    public Mono<Void> onSuccess(OrderPaymentDTO orderPaymentDTO) {
    	
    	return orderPaymentRepository.findByOrderId(orderPaymentDTO.getOrderId())
    			.switchIfEmpty(Mono.defer(() -> this.add(orderPaymentDTO, true)))
    			.then();
    }

    @Override
    public Mono<Void> onFailure(OrderPaymentDTO orderPaymentDTO) {
    	
    	return orderPaymentRepository.findByOrderId(orderPaymentDTO.getOrderId())
    			.switchIfEmpty(Mono.defer(() -> this.add(orderPaymentDTO, false)))
    			.then();
    }

    @Override
    public Mono<Void> onRollback(OrderPaymentDTO orderPaymentDTO) {
    	
    	return orderPaymentRepository.findByOrderId(orderPaymentDTO.getOrderId())
    			.doOnNext(e -> e.setStatus(orderPaymentDTO.getStatus()))
    			.flatMap(orderPaymentRepository::save)
    			.then();
    }

    private Mono<OrderPayment> add(OrderPaymentDTO orderPaymentDTO, boolean isSuccess) {
    	
    	OrderPayment orderPayment = orderMapper.toOrderPayment(orderPaymentDTO);
    	orderPayment.setSuccess(isSuccess);

    	return orderPaymentRepository.save(orderPayment);
    }

}
