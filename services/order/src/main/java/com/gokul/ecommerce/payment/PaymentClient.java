package com.gokul.ecommerce.payment;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.gokul.ecommerce.config.FeignOAuth2Config;

@FeignClient(
        name = "product-service",
        url = "${application.config.payment-url}",
        configuration = FeignOAuth2Config.class
)
public interface PaymentClient {

    @PostMapping
    Integer requestOrderPayment(@RequestBody PaymentRequest request);
}
