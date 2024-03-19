package com.ldsk.order.service.payment;

import com.ldsk.order.dto.OrderPaymentDTO;
import com.ldsk.order.service.OrderComponentStatusListener;

public interface PaymentComponentStatusListener extends OrderComponentStatusListener<OrderPaymentDTO> {
	
}
