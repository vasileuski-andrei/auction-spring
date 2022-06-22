package com.starlight.telegrambot.kafka;

import com.alibaba.fastjson.JSON;
import com.starlight.telegrambot.bot.Bot;
import com.starlight.telegrambot.dto.TelegramDataDto;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableKafka
@AllArgsConstructor
public class KafkaService {

    private static final String TOPIC = "bot";
    private final Bot bot;

    @KafkaListener(topics = TOPIC)
    public void receiveMessage(ConsumerRecord<String, List<TelegramDataDto>> consumerRecord) {
        var str = JSON.toJSON(consumerRecord.value());
        List<TelegramDataDto> telegramDataDtos = JSON.parseArray((String) str, TelegramDataDto.class);
        var telegramDataDto = telegramDataDtos.get(0);
        String responseText = "";

        if (telegramDataDto.getErrorMessage() != null) {
            responseText = telegramDataDto.getErrorMessage();

        } else if (telegramDataDto.getMessage().equals("my info")) {
            responseText = String.format("""
                Account information:
                id: %s
                Username: %s
                Email: %s
                Birth day: %s
                """, telegramDataDto.getId(),
                    telegramDataDto.getUsername(),
                    telegramDataDto.getEmail(),
                    telegramDataDto.getBirthDate());

        } else if (telegramDataDto.getMessage().equals("my lots")) {
            for (TelegramDataDto tgDataDto : telegramDataDtos) {
                responseText += String.format("""
                Lot name: %s
                Start price: %s
                Last bid: %s
                Username: %s
                """, tgDataDto.getLotName(),
                        tgDataDto.getStartBid(),
                        tgDataDto.getUserBid(),
                        tgDataDto.getUsername() + "\n");
            }
        } else if (telegramDataDto.getMessage().equals("all lots")) {
            for (TelegramDataDto tgDataDto : telegramDataDtos) {
                responseText += String.format("""
                Lot name: %s
                Lot owner: %s
                Start price: %s
                """, tgDataDto.getLotName(),
                        tgDataDto.getLotOwner(),
                        tgDataDto.getStartBid() + "\n");
            }
        }

        bot.sendAnswer(responseText);
    }

}
