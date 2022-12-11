package com.petdoctor.notification.listener;


import com.petdoctor.notification.service.impl.TelegramBotImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.petdoctor.notification.constant.KafkaConstant.KAFKA_APPOINTMENT_GROUP_ID;
import static com.petdoctor.notification.constant.KafkaConstant.KAFKA_APPOINTMENT_OPEN;

@AllArgsConstructor
@Service
@Slf4j
public class AppointmentListener {
    private final TelegramBotImpl telegramBot;

    @KafkaListener(
            id = "appointment_message_listener",
            groupId = KAFKA_APPOINTMENT_GROUP_ID,
            topics = KAFKA_APPOINTMENT_OPEN
    )
    public void appointmentMassageListener(String message) {
        int index = message.indexOf(' ');
        log.info("chat_id = " + message.substring(0, index) + "message = " + message.substring(index));
        telegramBot.sendMessage(message.substring(0, index), message.substring(index));
    }
}
