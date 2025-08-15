package com.gokul.ecommerce.config;

import com.gokul.ecommerce.kafka.order.OrderConfirmation;
import com.gokul.ecommerce.kafka.payment.PaymentConfirmation;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    private Map<String, Object> baseConsumerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "ecommerceGroup");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return props;
    }

    @Bean
    public ConsumerFactory<String, OrderConfirmation> orderConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                baseConsumerConfig(),
                new StringDeserializer(),
                new JsonDeserializer<>(OrderConfirmation.class, false)
        );
    }

    @Bean
    public ConsumerFactory<String, PaymentConfirmation> paymentConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                baseConsumerConfig(),
                new StringDeserializer(),
                new JsonDeserializer<>(PaymentConfirmation.class, false)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderConfirmation> orderKafkaListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OrderConfirmation> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(orderConsumerFactory());
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentConfirmation> paymentKafkaListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, PaymentConfirmation> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(paymentConsumerFactory());
        return factory;
    }
}
