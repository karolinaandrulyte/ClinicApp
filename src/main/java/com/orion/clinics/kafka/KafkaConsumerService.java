package com.orion.clinics.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "doctor-clinic-data", groupId = "patient-app-group")
    public void consumeDoctorClinicData(String message) {
        System.out.println("doctor-clinic-data: " + message);
    }

    @KafkaListener(topics = "auth-response", groupId = "patient-app-group")
    public void consumeAuthResponse(String message) {
        System.out.println("Received auth-response: " + message);
    }
}