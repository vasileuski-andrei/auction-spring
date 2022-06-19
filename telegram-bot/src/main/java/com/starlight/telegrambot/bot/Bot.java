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
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class Bot extends TelegramLongPollingBot {

    @Autowired
    private KafkaTemplate<String, UserInfoDto> kafkaTemplate;

    private static final String TOPIC_NAME = "auction";
    private Message message = null;

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
        message = update.getMessage();
        String messageText = message.getText();

        if (messageText.equals("/start")) {
            sendAnswer("Let's go");
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

    public void sendAnswer(String answer) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(answer);

        try {
            setButtons(sendMessage);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        replyKeyboardMarkup.setOneTimeKeyboard(false);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(new KeyboardButton("/settings"));
        keyboardRow.add(new KeyboardButton("my lots"));
        keyboardRow.add(new KeyboardButton("all lots"));
        keyboardRowList.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);

    }


}
