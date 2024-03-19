package com.ldsk.payment.service;

import com.ldsk.payment.dto.PaymentDTO;
import com.ldsk.payment.dto.PaymentProcessRequestDTO;
import com.ldsk.payment.dto.RefundProcessRequestDTO;

import reactor.core.publisher.Mono;

public interface PaymentService {

	Mono<PaymentDTO> process(PaymentProcessRequestDTO paymentProcessRequestDTO); 
	
	Mono<PaymentDTO> refund(RefundProcessRequestDTO refundProcessRequestDTO); 
	
}
