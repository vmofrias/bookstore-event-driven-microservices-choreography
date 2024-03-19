package com.ldsk.payment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ldsk.common.events.payment.PaymentStatus;
import com.ldsk.common.events.util.DuplicateEventValidator;
import com.ldsk.payment.dto.PaymentDTO;
import com.ldsk.payment.dto.PaymentProcessRequestDTO;
import com.ldsk.payment.dto.RefundProcessRequestDTO;
import com.ldsk.payment.exception.CustomerNotFoundException;
import com.ldsk.payment.exception.InsufficientBalanceException;
import com.ldsk.payment.mapper.PaymentMapper;
import com.ldsk.payment.model.Customer;
import com.ldsk.payment.model.Payment;
import com.ldsk.payment.repository.CustomerRepository;
import com.ldsk.payment.repository.PaymentRepository;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private PaymentMapper paymentMapper;
	
	@Override
	@Transactional
	public Mono<PaymentDTO> process(PaymentProcessRequestDTO paymentProcessRequestDTO) {
		
		return DuplicateEventValidator.validate(
					paymentRepository.existsByOrderId(paymentProcessRequestDTO.getOrderId()),
					customerRepository.findById(paymentProcessRequestDTO.getCustomerId())
				)
				.switchIfEmpty(Mono.error(new CustomerNotFoundException()))
				.filter(c -> c.getBalance() >= paymentProcessRequestDTO.getAmount())
				.switchIfEmpty(Mono.error(new InsufficientBalanceException()))
				.flatMap(c -> deductPayment(c, paymentProcessRequestDTO))
				.doOnNext(dto -> log.info("Payment processed for {}", dto.getOrderId()));
	}
	
	private Mono<PaymentDTO> deductPayment(Customer customer, PaymentProcessRequestDTO paymentProcessRequestDTO) {
		
		Payment payment = paymentMapper.toPayment(paymentProcessRequestDTO);
		
		customer.setBalance(customer.getBalance() - paymentProcessRequestDTO.getAmount());
		payment.setStatus(PaymentStatus.DEDUCTED);
		
		return customerRepository.save(customer)
				.then(paymentRepository.save(payment))
				.map(paymentMapper::toPaymentDto);
	}

	@Override
	@Transactional
	public Mono<PaymentDTO> refund(RefundProcessRequestDTO refundProcessRequestDTO) {
		
		return paymentRepository.findByOrderIdAndStatus(refundProcessRequestDTO.getOrderId(), PaymentStatus.DEDUCTED)
				.zipWhen(p -> customerRepository.findById(p.getCustomerId()))
				.flatMap(t -> refundPayment(t.getT1(), t.getT2()))
				.doOnNext(dto -> log.info("Refunded amount {} for {}", dto.getAmount(), dto.getOrderId()));
	}
	
	private Mono<PaymentDTO> refundPayment(Payment payment, Customer customer) {
		
		customer.setBalance(customer.getBalance() + payment.getAmount());
		payment.setStatus(PaymentStatus.REFUNDED);
		
		return customerRepository.save(customer)
				.then(paymentRepository.save(payment))
				.map(paymentMapper::toPaymentDto);
	}

}
