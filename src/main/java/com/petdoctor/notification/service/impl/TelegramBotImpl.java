package com.petdoctor.notification.service.impl;

import com.petdoctor.notification.constant.KafkaConstant;
import com.petdoctor.notification.service.KafkaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Slf4j
@Component
@AllArgsConstructor
public class TelegramBotImpl extends TelegramLongPollingBot {

    private final KafkaService kafkaService;

    @Override
    public String getBotUsername() {
        return "PetDoctorBot";
    }

    @Override
    public String getBotToken() {
        return "5416536642:AAGVEzg-7Sr3jyxGDsXYVuv49RQ7e2iaXU0";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message_text = update.getMessage().getText();
            String chat_id = update.getMessage().getChatId().toString();

            SendMessage message = SendMessage.builder().text(message_text).chatId(chat_id).build();
            try {
                kafkaService.sendMessage(chat_id, KafkaConstant.KAFKA_APPOINTMENT_CHAT_ID);
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(textToSend);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("ERROR_TEXT" + e.getMessage());
        }
    }
}