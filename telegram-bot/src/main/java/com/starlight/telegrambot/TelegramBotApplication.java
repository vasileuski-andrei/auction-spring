package com.starlight.telegrambot;

import com.starlight.telegrambot.bot.Bot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class TelegramBotApplication {

    private static Bot bot = null;

    @Autowired
    public TelegramBotApplication(Bot bot) {
        TelegramBotApplication.bot = bot;
    }

    public static void main(String[] args) {
        SpringApplication.run(TelegramBotApplication.class, args);
        registerTelegramBot();
    }

    private static void registerTelegramBot() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
