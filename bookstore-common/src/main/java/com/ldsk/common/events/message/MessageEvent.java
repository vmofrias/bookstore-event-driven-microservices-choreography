package com.ldsk.common.events.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.kafka.receiver.ReceiverOffset;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageEvent <T> {
	
	private T message;
	private String key;
	private ReceiverOffset acknowledgement;
	
}
