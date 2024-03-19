package com.ldsk.order.mapper;

import org.springframework.stereotype.Component;

import com.ldsk.common.events.order.OrderStatus;
import com.ldsk.order.dto.OrderCreateRequestDTO;
import com.ldsk.order.dto.OrderDetailsDTO;
import com.ldsk.order.dto.OrderInventoryDTO;
import com.ldsk.order.dto.OrderPaymentDTO;
import com.ldsk.order.dto.PurchaseOrderDTO;
import com.ldsk.order.model.OrderInventory;
import com.ldsk.order.model.OrderPayment;
import com.ldsk.order.model.PurchaseOrder;


@Component
public class OrderMapper {

    public PurchaseOrder toPurchaseOrder(OrderCreateRequestDTO orderCreateRequestDTO) {
    	
    	return PurchaseOrder.builder()
	    			.customerId(orderCreateRequestDTO.getCustomerId())
	    			.productId(orderCreateRequestDTO.getProductId())
	    			.quantity(orderCreateRequestDTO.getQuantity())
	    			.unitPrice(orderCreateRequestDTO.getUnitPrice())
	    			.amount(orderCreateRequestDTO.getQuantity() * orderCreateRequestDTO.getUnitPrice())
	    			.status(OrderStatus.PENDING)
    			.build();
    }

    public PurchaseOrderDTO toPurchaseOrderDTO(PurchaseOrder purchaseOrder) {
    	
    	return PurchaseOrderDTO.builder()
	    			.orderId(purchaseOrder.getOrderId())
	    			.unitPrice(purchaseOrder.getUnitPrice())
	    			.quantity(purchaseOrder.getQuantity())
	    			.productId(purchaseOrder.getProductId())
	    			.amount(purchaseOrder.getAmount())
	    			.customerId(purchaseOrder.getCustomerId())
	    			.status(purchaseOrder.getStatus())
	    			.deliveryDate(purchaseOrder.getDeliveryDate())
    			.build();
    }

    public OrderPayment toOrderPayment(OrderPaymentDTO orderPaymentDTO) {
    	
    	return OrderPayment.builder()
	    			.paymentId(orderPaymentDTO.getPaymentId())
	    			.orderId(orderPaymentDTO.getOrderId())
	    			.message(orderPaymentDTO.getMessage())
	    			.status(orderPaymentDTO.getStatus())
    			.build();
    }

    public OrderPaymentDTO toOrderPaymentDTO(OrderPayment orderPayment) {
    	
    	return OrderPaymentDTO.builder()
	    			.paymentId(orderPayment.getPaymentId())
	    			.orderId(orderPayment.getOrderId())
	    			.message(orderPayment.getMessage())
	    			.status(orderPayment.getStatus())
    			.build();
    }

    public OrderInventory toOrderInventory(OrderInventoryDTO orderInventoryDTO) {
    	
    	return OrderInventory.builder()
	    			.inventoryId(orderInventoryDTO.getInventoryId())
	    			.orderId(orderInventoryDTO.getOrderId())
	    			.message(orderInventoryDTO.getMessage())
	    			.status(orderInventoryDTO.getStatus())
    			.build();
    }

    public OrderInventoryDTO toOrderInventoryDTO(OrderInventory orderInventory) {
    	
    	return OrderInventoryDTO.builder()
	    			.inventoryId(orderInventory.getInventoryId())
	    			.orderId(orderInventory.getOrderId())
	    			.message(orderInventory.getMessage())
	    			.status(orderInventory.getStatus())
    			.build();
    }
    
	public OrderDetailsDTO toOrderDetailsDTO(PurchaseOrderDTO purchaseOrderDTO, OrderPaymentDTO orderPaymentDTO,
			OrderInventoryDTO orderInventoryDTO) {

		
		return OrderDetailsDTO.builder()
					.purchaseOrderDTO(purchaseOrderDTO)
					.orderPaymentDTO(orderPaymentDTO)
					.orderInventoryDTO(orderInventoryDTO)
				.build();
	}
	
}
