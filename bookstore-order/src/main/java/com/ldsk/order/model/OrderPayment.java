package com.ldsk.order.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;

import com.ldsk.common.events.payment.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderPayment {
	
    @Id
    private Integer id;
    private UUID orderId;
    private UUID paymentId;
    private PaymentStatus status;
    private Boolean success;
    private String message;

}
