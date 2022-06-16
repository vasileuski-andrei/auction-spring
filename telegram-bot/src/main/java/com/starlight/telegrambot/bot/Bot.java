package com.starlight.telegrambot.bot;

import com.starlight.telegrambot.dto.UserInfoDto;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class Bot extends TelegramLongPollingBot {

    @Autowired
    private KafkaTemplate<String, UserInfoDto> kafkaTemplate;

    private static final String TOPIC_NAME = "auction";

    @Value("${spring.kafka.botname}")
    private String botname;
    @Value("${spring.kafka.bottoken}")
    private String bottoken;

    @Override
    public String getBotUsername() {
        return botname;
    }

    @Override
    public String getBotToken() {
        return bottoken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        String messageText = message.getText();

        if (messageText.equals("/start")) {
            sendAnswer(message.getChatId().toString(), "Let's go");
        } else if (messageText.equals("info")) {
            var listenableFuture = kafkaTemplate.send(TOPIC_NAME, buildUserInfoDto(message));
            listenableFuture.addCallback(System.out::println, System.err::println);
        }

    }

    private UserInfoDto buildUserInfoDto(Message message) {
        return UserInfoDto.builder()
                .message(message.getText())
                .telegramAccount(message.getFrom().getUserName())
                .build();
    }

    public void sendAnswer(String chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


}
