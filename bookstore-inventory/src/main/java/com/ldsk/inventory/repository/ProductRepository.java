package com.ldsk.inventory.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.ldsk.inventory.model.Product;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Integer> {

}
