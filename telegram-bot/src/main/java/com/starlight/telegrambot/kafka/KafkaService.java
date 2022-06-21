package com.starlight.telegrambot.kafka;

import com.alibaba.fastjson.JSON;
import com.starlight.telegrambot.bot.Bot;
import com.starlight.telegrambot.dto.UserInfoDto;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@EnableKafka
@AllArgsConstructor
public class KafkaService {

    private static final String TOPIC = "bot";
    private final Bot bot;

    @KafkaListener(topics = TOPIC)
    public void receiveMessage(ConsumerRecord<String, UserInfoDto> consumerRecord) {
        UserInfoDto userDto = JSON.parseObject(String.valueOf(consumerRecord.value()), UserInfoDto.class);

        String responseText = String.format("""
                Account information.
                id: %s
                Username: %s
                Email: %s
                Birth day: %s
                """, userDto.getId(), userDto.getUsername(), userDto.getEmail(), userDto.getBirthDate());

        bot.sendAnswer(responseText);

    }

}
