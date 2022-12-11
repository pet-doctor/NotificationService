package com.petdoctor.notification.service;

public interface KafkaService {
    void sendMessage(Object payload, String topic);
}
