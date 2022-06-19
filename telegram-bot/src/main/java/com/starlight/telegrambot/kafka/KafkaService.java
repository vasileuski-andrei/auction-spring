package com.starlight.telegrambot.kafka;

import com.alibaba.fastjson.JSON;
import com.starlight.telegrambot.bot.Bot;
import com.starlight.telegrambot.dto.UserDto;
import com.starlight.telegrambot.dto.UserInfoDto;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@EnableKafka
@AllArgsConstructor
public class KafkaService {

    private static final String TOPIC = "bot";

    private final KafkaTemplate<String, UserInfoDto> kafkaTemplate;
    private final Bot bot;

    @KafkaListener(topics = "bot")
    public void receiveMessage(ConsumerRecord<String, UserDto> consumerRecord) {
        System.out.println("receiveMessage!!!");
        UserDto userDto = JSON.parseObject(String.valueOf(consumerRecord.value()), UserDto.class);
        bot.sendAnswer("Test");

    }

}
