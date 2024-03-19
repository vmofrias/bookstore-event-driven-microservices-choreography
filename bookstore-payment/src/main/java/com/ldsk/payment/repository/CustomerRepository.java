package com.ldsk.payment.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.ldsk.payment.model.Customer;

@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer> {

}
