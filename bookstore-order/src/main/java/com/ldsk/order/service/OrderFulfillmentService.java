package com.ldsk.order.service;

import java.util.UUID;

import com.ldsk.order.dto.PurchaseOrderDTO;

import reactor.core.publisher.Mono;

public interface OrderFulfillmentService {

    Mono<PurchaseOrderDTO> complete(UUID orderId);

    Mono<PurchaseOrderDTO> cancel(UUID orderId);

}
