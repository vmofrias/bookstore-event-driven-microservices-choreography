package com.ldsk.common.events.shipping;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShippingScheduledEvent implements ShippingEvent {
	
	private UUID orderId; 
	private Instant createdAt;
	private UUID shipmentId;
	private Instant expectedDelivery;
	
}
