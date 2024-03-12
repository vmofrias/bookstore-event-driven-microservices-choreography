package com.ldsk.common.events.util;

import java.util.function.Function;

import com.ldsk.common.events.exception.EventAlreadyProcessedException;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
public class DuplicateEventValidator {

	private DuplicateEventValidator() {
		throw new IllegalStateException("Utility class");
	}
	
	public static Function<Mono<Boolean>, Mono<Void>> emitErrorForRedundantProcessing(){
		
		return mono -> mono.flatMap(b -> Boolean.TRUE.equals(b) ? Mono.error(new EventAlreadyProcessedException()) : Mono.empty())
				.doOnError(EventAlreadyProcessedException.class, ex -> log.warn("Duplicate event."))
				.then();
	}
	
	public static <T> Mono<T> validate(Mono<Boolean> eventValidationPublisher, Mono<T> eventProcessingPublisher) {
		
		return eventValidationPublisher
				.transform(emitErrorForRedundantProcessing())
				.then(eventProcessingPublisher);
	}
	
}
