package com.ldsk.common.events.util;

import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;

import com.ldsk.common.events.message.MessageEvent;

import reactor.kafka.receiver.ReceiverOffset;

public class MessageEventConverter {
	
	private MessageEventConverter() {
		throw new IllegalStateException("Utility class");
	}

	public static <T> MessageEvent<T> toMessageEvent(Message<T> message) {
		
		T payload = message.getPayload();
		String key = message.getHeaders().get(KafkaHeaders.RECEIVED_KEY, String.class);
		ReceiverOffset ack = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, ReceiverOffset.class);
		
		return new MessageEvent<>(payload, key, ack);
	}
	
}
