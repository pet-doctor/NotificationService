package com.petdoctor.notification.service.impl;

import com.petdoctor.notification.service.KafkaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaServiceImpl implements KafkaService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void sendMessage(Object payload, String topic) {
        String message;
        log.info("current payload: " + payload.toString());
        message = payload.toString();

        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, message);

        kafkaTemplate.send(producerRecord);
    }
}