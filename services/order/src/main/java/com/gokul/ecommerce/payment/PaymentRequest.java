package com.gokul.ecommerce.payment;

import com.gokul.ecommerce.customer.CustomerResponse;
import com.gokul.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
    BigDecimal amount,
    PaymentMethod paymentMethod,
    Integer orderId,
    String orderReference,
    CustomerResponse customer
) {
}
