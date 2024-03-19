package com.ldsk.order.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ldsk.order.dto.OrderCreateRequestDTO;
import com.ldsk.order.dto.OrderDetailsDTO;
import com.ldsk.order.dto.PurchaseOrderDTO;
import com.ldsk.order.service.OrderService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping("/all")
	public Flux<PurchaseOrderDTO> getAllOrders() {
    	
    	return orderService.getAllOrders();
    }
    
    @GetMapping("/{orderId}")
    public Mono<OrderDetailsDTO> getOrderDetails(@PathVariable UUID orderId) {
    	
    	return orderService.getOrderDetails(orderId);
    }
    
    
    @PostMapping
    public Mono<ResponseEntity<PurchaseOrderDTO>> placeOrder(@RequestBody Mono<OrderCreateRequestDTO> orderCreateRequestMono) {
    	
    	return orderCreateRequestMono.flatMap(orderService::placeOrder)
    			.map(ResponseEntity.accepted()::body);
    }

}
