package com.ldsk.payment.messaging.processor;

import java.util.function.UnaryOperator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldsk.common.events.exception.EventAlreadyProcessedException;
import com.ldsk.common.events.order.OrderCancelledEvent;
import com.ldsk.common.events.order.OrderCompletedEvent;
import com.ldsk.common.events.order.OrderCreatedEvent;
import com.ldsk.common.events.payment.PaymentEvent;
import com.ldsk.common.processor.OrderEventProcessor;
import com.ldsk.payment.messaging.mapper.MessageMapper;
import com.ldsk.payment.service.PaymentServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Service
@Log4j2
@RequiredArgsConstructor
public class OrderEventProcessorImpl implements OrderEventProcessor<PaymentEvent> {

	@Autowired
	private PaymentServiceImpl paymentServiceImpl;
	
	@Autowired
	private MessageMapper messageMapper;
	
	@Override
	public Mono<PaymentEvent> handleOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent) {
		
		return paymentServiceImpl.process(messageMapper.toPaymentProcessRequestDTO(orderCreatedEvent))
				.map(messageMapper::toPaymentDeductedEvent)
				.doOnNext(e -> log.info("Payment processed {}", e))
				.transform(exceptionHandler(orderCreatedEvent));
	}
	
	@Override
	public Mono<PaymentEvent> handleOrderCancelledEvent(OrderCancelledEvent orderCancelledEvent) {
		
		return paymentServiceImpl.refund(messageMapper.toRefundProcessRequestDTO(orderCancelledEvent))
				.map(messageMapper::toPaymentRefundedEvent)
				.doOnNext(e -> log.info("Refund processed {}", e))
				.doOnError(ex -> log.error("Error while processing refund.", ex));
	}

	@Override
	public Mono<PaymentEvent> handleOrderCompletedEvent(OrderCompletedEvent orderCompletedEvent) {
		
		return Mono.empty();
	}

    private UnaryOperator<Mono<PaymentEvent>> exceptionHandler(OrderCreatedEvent orderCreatedEvent) {
        return mono -> mono.onErrorResume(EventAlreadyProcessedException.class, e -> Mono.empty())
                           .onErrorResume(messageMapper.toPaymentDeclinedEvent(orderCreatedEvent));
    }
    
}
