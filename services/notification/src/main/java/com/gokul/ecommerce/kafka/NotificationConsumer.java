package com.gokul.ecommerce.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gokul.ecommerce.email.EmailService;
import com.gokul.ecommerce.kafka.order.OrderConfirmation;
import com.gokul.ecommerce.kafka.payment.PaymentConfirmation;
import com.gokul.ecommerce.notification.Notification;
import com.gokul.ecommerce.notification.NotificationRepository;
import com.gokul.ecommerce.notification.NotificationType;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    //        ObjectMapper mapper = new ObjectMapper();
//        PaymentConfirmation paymentConfirmation= mapper.readValue(confirmation, PaymentConfirmation.class);
    @KafkaListener(topics = "payment-topic", groupId = "ecommerceGroup")
    public void consumePaymentSuccessNotification(PaymentConfirmation paymentConfirmation) throws MessagingException, JsonProcessingException {

        log.info("Consuming the message from payment-topic:: {}", paymentConfirmation);
        notificationRepository.save(
                Notification.builder()
                        .type(NotificationType.PAYMENT_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .paymentConfirmation(paymentConfirmation)
                        .build()
        );

        var customerName = paymentConfirmation.customerFirstname() + " " + paymentConfirmation.customerLastname();
        emailService.sendPaymentSuccessEmail(
                paymentConfirmation.customerEmail(),
                customerName,
                paymentConfirmation.amount(),
                paymentConfirmation.orderReference()
        );
    }

    //    ObjectMapper mapper = new ObjectMapper();
//    OrderConfirmation confirmation = mapper.readValue(orderConfirmation, OrderConfirmation.class);
    @KafkaListener(topics = "order-topic", groupId = "ecommerceGroup")
    public void consumeOrderConfirmationNotification(OrderConfirmation confirmation) throws MessagingException, JsonProcessingException {

        log.info("Consuming the message from order-topic:: {}", confirmation);
        notificationRepository.save(
                Notification.builder()
                        .type(NotificationType.PAYMENT_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .orderConfirmation(confirmation)
                        .build()
        );

        var customerName = confirmation.customer().firstName() + " " + confirmation.customer().lastName();
        emailService.sendOrderConfirmationEmail(
                confirmation.customer().email(),
                customerName,
                confirmation.totalAmount(),
                confirmation.orderReference(),
                confirmation.products()
        );
    }
}
