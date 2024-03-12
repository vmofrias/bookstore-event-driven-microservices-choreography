package com.ldsk.common.events.shipping;

import java.time.Instant;
import java.util.UUID;

import com.ldsk.common.events.OrderSaga;

import lombok.Builder;
import lombok.Getter;


@Getter
public class ShippingScheduledEvent extends OrderSaga {
	
	@Builder
	public ShippingScheduledEvent(UUID orderId, Instant createdAt, Integer shipmentId,
			Instant expectedDelivery) {
		
		super(orderId, createdAt);
		
		this.shipmentId = shipmentId;
		this.expectedDelivery = expectedDelivery;
	}

	private Integer shipmentId;
	private Instant expectedDelivery;
	
}
