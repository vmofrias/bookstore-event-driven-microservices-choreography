package com.ldsk.common.events;

import java.time.Instant;
import java.util.UUID;

public interface DomainEvent {

	UUID getOrderId();
	Instant getCreatedAt();
	
}
