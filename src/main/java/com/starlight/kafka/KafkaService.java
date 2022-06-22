package com.starlight.kafka;

import com.alibaba.fastjson.JSON;
import com.starlight.dto.TelegramDataDto;
import com.starlight.model.Bid;
import com.starlight.model.Lot;
import com.starlight.model.User;
import com.starlight.service.LotService;
import com.starlight.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@EnableKafka
@Component
@AllArgsConstructor
public class KafkaService {

    private static final String TOPIC = "bot";

    private final UserService userService;
    private final LotService lotService;
    private KafkaTemplate<String, List<TelegramDataDto>> kafkaTemplate;
    private ModelMapper modelMapper;

    @KafkaListener(topics = "auction")
    public void receiveMessage(ConsumerRecord<String, TelegramDataDto> consumerRecord) {
        TelegramDataDto telegramDataDto = JSON.parseObject(String.valueOf(consumerRecord.value()), TelegramDataDto.class);
        String telegramMessage = telegramDataDto.getMessage();
        List<TelegramDataDto> tgDataDtoList = new ArrayList<>();

        if (telegramMessage.equals("my info")) {
            User user = userService.getUserByTelegramAccount(telegramDataDto.getTelegramAccount());

            if (user != null) {
                var mappedTgDataDto = modelMapper.map(user, TelegramDataDto.class);
                mappedTgDataDto.setMessage(telegramDataDto.getMessage());
                tgDataDtoList.add(mappedTgDataDto);
            } else {
                createTelegramDataDto(tgDataDtoList, "Your telegram account isn't valid");
            }

        } else if (telegramMessage.equals("my lots")) {
            User user = userService.getUserByTelegramAccount(telegramDataDto.getTelegramAccount());

            if (user != null) {
                var userLots = user.getLots();

                if (userLots != null) {

                    var telegramDataDtos = convertToTelegramDataDtoList(userLots);
                    telegramDataDtos.get(0).setMessage(telegramDataDto.getMessage());
                    setMaxUserBid(userLots, telegramDataDtos);
                    tgDataDtoList.addAll(telegramDataDtos);
                } else {
                    createTelegramDataDto(tgDataDtoList, "You don't have any lots");
                }

            } else {
                createTelegramDataDto(tgDataDtoList, "Your telegram account isn't valid");
            }
        } else if (telegramMessage.equals("all lots")) {
            var allLotDto = lotService.getAllLot();
            var telegramDataDtos = convertToTelegramDataDtoList(allLotDto);
            telegramDataDtos.get(0).setMessage(telegramDataDto.getMessage());
            tgDataDtoList.addAll(telegramDataDtos);

        }

        sendMessage(tgDataDtoList);
    }

    private void createTelegramDataDto(List<TelegramDataDto> tgDataDtoList, String errorMessage) {
        tgDataDtoList.add(TelegramDataDto.builder()
                .errorMessage("Your telegram account isn't valid.")
                .build());
    }

    public void sendMessage(List<TelegramDataDto> telegramDataDto) {
        kafkaTemplate.send(TOPIC, telegramDataDto);
    }

    private List<TelegramDataDto> convertToTelegramDataDtoList(List<?> allLotDto) {
        List<TelegramDataDto> tgDataDtoList = new ArrayList<>();
        for (Object lotDto : allLotDto) {
            tgDataDtoList.add(modelMapper.map(lotDto, TelegramDataDto.class));
        }
        return tgDataDtoList;
    }

    private void setMaxUserBid(List<Lot> allLots, List<TelegramDataDto> telegramDataDto) {
        for (int i = 0; i < allLots.size(); i++) {
            var bids = allLots.get(i).getBids();
            if (!bids.isEmpty()) {
                var sortedBids = bids.stream().sorted(Comparator.comparing(Bid::getUserBid).reversed()).toList();

                telegramDataDto.get(i).setUserBid(sortedBids.get(0).getUserBid());
                telegramDataDto.get(i).setUsername(sortedBids.get(0).getUsername());
            }
        }
    }

}
