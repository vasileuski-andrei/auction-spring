package com.starlight.kafka;

import com.starlight.dto.UserInfoDto;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@EnableKafka
@Component
public class KafkaReceiver {

    @KafkaListener(topics = "auction")
    void Listener(ConsumerRecord<String, UserInfoDto> consumerRecord) {
        System.out.println(consumerRecord.partition());
        System.out.println(consumerRecord.key());
        System.out.println(consumerRecord.value());
    }

}
