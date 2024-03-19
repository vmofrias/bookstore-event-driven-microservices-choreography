package com.ldsk.order.messaging.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ldsk.common.events.order.OrderEvent;
import com.ldsk.order.messaging.publisher.OrderEventListenerImpl;
import com.ldsk.order.service.OrderEventListener;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;

@Configuration
public class OrderEventListenerConfig {

    @Bean
    public OrderEventListener orderEventListener() {
    	
    	Many<OrderEvent> sink = Sinks.many().unicast().<OrderEvent>onBackpressureBuffer();
    	Flux<OrderEvent> flux = sink.asFlux();
    	
    	return new OrderEventListenerImpl(sink, flux);
    }

}
