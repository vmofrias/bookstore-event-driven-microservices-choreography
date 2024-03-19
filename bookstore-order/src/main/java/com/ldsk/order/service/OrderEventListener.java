package com.ldsk.order.service;

import com.ldsk.order.dto.PurchaseOrderDTO;

public interface OrderEventListener {

    void emitOrderCreated(PurchaseOrderDTO purchaseOrderDTO);

}
