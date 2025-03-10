package com.orion.clinics.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public <T> void sendMessage(String topic, String eventType, T payload) {
        KafkaMessage<T> message = new KafkaMessage<>(eventType, payload);
        kafkaTemplate.send(topic, message);
    }
}