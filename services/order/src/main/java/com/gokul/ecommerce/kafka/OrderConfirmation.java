package com.gokul.ecommerce.kafka;

import com.gokul.ecommerce.customer.CustomerResponse;
import com.gokul.ecommerce.order.PaymentMethod;
import com.gokul.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
