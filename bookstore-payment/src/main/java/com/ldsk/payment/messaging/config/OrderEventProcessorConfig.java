package com.ldsk.payment.messaging.config;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import com.ldsk.common.events.order.OrderEvent;
import com.ldsk.common.events.payment.PaymentEvent;
import com.ldsk.common.events.util.MessageEventConverter;
import com.ldsk.common.processor.OrderEventProcessor;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

@Configuration
@Log4j2
public class OrderEventProcessorConfig {

	@Autowired
	private OrderEventProcessor<PaymentEvent> orderEventProcessor;
	
	@Bean
	public Function<Flux<Message<OrderEvent>>, Flux<Message<PaymentEvent>>> processor() {
		
		return flux -> flux.map(MessageEventConverter::toMessageEvent)
				.doOnNext(r -> log.info("Payment received {}", r.getMessage()))
				.concatMap(r -> orderEventProcessor.process(r.getMessage())
						.doOnSuccess(e -> r.getAcknowledgement().acknowledge())
			).map(this::toMessage);
	}
	
    private Message<PaymentEvent> toMessage(PaymentEvent paymentEvent) {
    	
        return MessageBuilder.withPayload(paymentEvent)
                             .setHeader(KafkaHeaders.KEY, paymentEvent.getOrderId().toString())
                             .build();
    }
	
}
