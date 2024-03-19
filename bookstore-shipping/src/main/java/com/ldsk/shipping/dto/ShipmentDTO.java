package com.ldsk.shipping.dto;

import java.time.Instant;
import java.util.UUID;

import com.ldsk.common.events.shipping.ShippingStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentDTO {

	private UUID shipmentId;
	private UUID orderId;
	private Integer productId;
	private Integer customerId;
	private Integer quantity;
	private Instant deliveryDate;
	private ShippingStatus status;
	
}
