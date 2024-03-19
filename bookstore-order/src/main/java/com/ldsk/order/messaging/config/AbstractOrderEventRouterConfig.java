package com.ldsk.order.messaging.config;

import java.util.function.Function;

import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import com.ldsk.common.events.DomainEvent;
import com.ldsk.common.events.order.OrderEvent;
import com.ldsk.common.events.util.MessageEventConverter;
import com.ldsk.common.processor.EventProcessor;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

@Log4j2
public abstract class AbstractOrderEventRouterConfig {

    private static final String DESTINATION_HEADER = "spring.cloud.stream.sendto.destination";
    private static final String ORDER_EVENTS_CHANNEL = "order-events-channel";

    protected <T extends DomainEvent> Function<Flux<Message<T>>, Flux<Message<OrderEvent>>> processor(EventProcessor<T, OrderEvent> eventProcessor) {
        
    	return flux -> flux.map(MessageEventConverter::toMessageEvent)
                           .doOnNext(r -> log.info("Order service received {}", r.getMessage()))
                           .concatMap(r -> eventProcessor.process(r.getMessage())
                                                              .doOnSuccess(e -> r.getAcknowledgement().acknowledge())
                           )
                           .map(this::toMessage);
    }

    protected Message<OrderEvent> toMessage(OrderEvent orderSaga) {
    	
    	log.info("Order service produced {}", orderSaga);
    	
        return MessageBuilder.withPayload(orderSaga)
        					 .setHeader(KafkaHeaders.KEY, orderSaga.getOrderId().toString())
                             .setHeader(DESTINATION_HEADER, ORDER_EVENTS_CHANNEL)
                             .build();
    }
    
}
