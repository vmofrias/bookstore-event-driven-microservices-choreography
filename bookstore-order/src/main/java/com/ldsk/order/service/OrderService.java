package com.ldsk.order.service;

import java.util.UUID;

import com.ldsk.order.dto.OrderCreateRequestDTO;
import com.ldsk.order.dto.OrderDetailsDTO;
import com.ldsk.order.dto.PurchaseOrderDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderService {

    Mono<PurchaseOrderDTO> placeOrder(OrderCreateRequestDTO orderCreateRequestDTO);

    Flux<PurchaseOrderDTO> getAllOrders();

    Mono<OrderDetailsDTO> getOrderDetails(UUID orderId);

}
