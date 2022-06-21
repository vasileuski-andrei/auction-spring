package com.starlight.kafka;

import com.alibaba.fastjson.JSON;
import com.starlight.dto.UserInfoDto;
import com.starlight.model.User;
import com.starlight.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@EnableKafka
@Component
@AllArgsConstructor
public class KafkaService {

    private static final String TOPIC = "bot";

    private final UserService userService;
    private KafkaTemplate<String, UserInfoDto> kafkaTemplate;
    private ModelMapper modelMapper;

    @KafkaListener(topics = "auction")
    public void receiveMessage(ConsumerRecord<String, UserInfoDto> consumerRecord) {
        UserInfoDto userInfoDto = JSON.parseObject(String.valueOf(consumerRecord.value()), UserInfoDto.class);

        if (userInfoDto.getMessage().equals("my info")) {
            User user = userService.getUserByTelegramAccount(userInfoDto.getTelegramAccount());
            sendMessage(modelMapper.map(user, UserInfoDto.class));
        }
    }

    public void sendMessage(UserInfoDto userInfoDto) {
        kafkaTemplate.send(TOPIC, userInfoDto);
    }
}
