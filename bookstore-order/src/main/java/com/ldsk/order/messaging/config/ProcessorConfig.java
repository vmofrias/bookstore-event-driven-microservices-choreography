package com.ldsk.order.messaging.config;

import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import com.ldsk.common.events.inventory.InventoryEvent;
import com.ldsk.common.events.order.OrderEvent;
import com.ldsk.common.events.payment.PaymentEvent;
import com.ldsk.common.events.shipping.ShippingEvent;
import com.ldsk.common.processor.EventProcessor;
import com.ldsk.common.publisher.EventPublisher;

import reactor.core.publisher.Flux;

@Configuration
public class ProcessorConfig extends AbstractOrderEventRouterConfig {
	
    @Bean
    public Function<Flux<Message<InventoryEvent>>, Flux<Message<OrderEvent>>> inventoryProcessor(EventProcessor<InventoryEvent, OrderEvent> inventoryEventProcessor){
    	
        return this.processor(inventoryEventProcessor);
    }
    
    @Bean
    public Function<Flux<Message<PaymentEvent>>, Flux<Message<OrderEvent>>> paymentProcessor(EventProcessor<PaymentEvent, OrderEvent> paymentEventProcessor){
    	
        return this.processor(paymentEventProcessor);
    }
    
    @Bean
    public Function<Flux<Message<ShippingEvent>>, Flux<Message<OrderEvent>>> shippingProcessor(EventProcessor<ShippingEvent, OrderEvent> shippingEventProcessor){
    	
        return this.processor(shippingEventProcessor);
    }

    @Bean
    public Supplier<Flux<Message<OrderEvent>>> orderEventProducer(EventPublisher<OrderEvent> eventPublisher) {
    	
        return () -> eventPublisher.publish().map(this::toMessage);
    }

}
