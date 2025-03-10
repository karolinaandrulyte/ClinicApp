package com.orion.clinics.kafka;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.ALWAYS)
public class KafkaMessage<T> {

    private String eventType;
    private T payload;

    public KafkaMessage(String eventType, T payload) {
        this.eventType = eventType;
        this.payload = payload;
    }

}