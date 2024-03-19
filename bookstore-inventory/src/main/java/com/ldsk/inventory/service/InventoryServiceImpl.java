package com.ldsk.inventory.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ldsk.common.events.inventory.InventoryStatus;
import com.ldsk.common.events.util.DuplicateEventValidator;
import com.ldsk.inventory.dto.InventoryDeductRequestDTO;
import com.ldsk.inventory.dto.OrderInventoryDTO;
import com.ldsk.inventory.exception.OutOfStockException;
import com.ldsk.inventory.mapper.InventoryMapper;
import com.ldsk.inventory.model.Inventory;
import com.ldsk.inventory.model.Product;
import com.ldsk.inventory.repository.InventoryRepository;
import com.ldsk.inventory.repository.ProductRepository;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Service
@Log4j2
public class InventoryServiceImpl implements InventoryService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private InventoryRepository inventoryRepository;
	
	@Autowired
	private InventoryMapper inventoryMapper;
	
	@Override
	@Transactional
	public Mono<OrderInventoryDTO> deduct(InventoryDeductRequestDTO inventoryDeductRequestDTO) {
		
		return DuplicateEventValidator.validate(
				inventoryRepository.existsByOrderId(inventoryDeductRequestDTO.getOrderId()), 
				productRepository.findById(inventoryDeductRequestDTO.getProductId()))
				.filter(p -> p.getAvailableQuantity() >= inventoryDeductRequestDTO.getQuantity())
				.switchIfEmpty(Mono.error(new OutOfStockException()))
				.flatMap(p -> deductInventory(p, inventoryDeductRequestDTO))
				.doOnNext(dto -> log.info("Inventory deducted for {}", dto.getOrderId()));
	}
	
    private Mono<OrderInventoryDTO> deductInventory(Product product, InventoryDeductRequestDTO inventoryDeductRequestDTO) {
    	
    	Inventory inventory = inventoryMapper.toInventory(inventoryDeductRequestDTO);
    	
    	product.setAvailableQuantity(product.getAvailableQuantity() - inventoryDeductRequestDTO.getQuantity());
    	inventory.setStatus(InventoryStatus.DEDUCTED);
    	
    	return productRepository.save(product)
    			.then(inventoryRepository.save(inventory))
    			.map(inventoryMapper::toOrderInventoryDTO);
    }
    
	@Override
	@Transactional
	public Mono<OrderInventoryDTO> restore(UUID orderId) {
		
		return inventoryRepository.findByOrderIdAndStatus(orderId, InventoryStatus.DEDUCTED)
				.zipWhen(i -> productRepository.findById(i.getProductId()))
				.flatMap(t -> restore(t.getT1(), t.getT2()))
				.doOnNext(dto -> log.info("Restored quantity {} for {}", dto.getQuantity(), dto.getOrderId()));
	}
	
	private Mono<OrderInventoryDTO> restore(Inventory inventory, Product product) {
		
		product.setAvailableQuantity(product.getAvailableQuantity() + inventory.getQuantity());
		inventory.setStatus(InventoryStatus.RESTORED);
		
		return productRepository.save(product)
				.then(inventoryRepository.save(inventory))
				.map(inventoryMapper::toOrderInventoryDTO);
	}

}
