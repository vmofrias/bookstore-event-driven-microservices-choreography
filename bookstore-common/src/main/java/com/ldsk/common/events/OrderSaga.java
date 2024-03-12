package com.ldsk.common.events;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderSaga {

	private UUID orderId;
	private Instant createdAt;
	
}
