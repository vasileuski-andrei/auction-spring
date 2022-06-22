package com.starlight.telegrambot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TelegramDataDto {

    private Long id;
    private String username;
    private String birthDate;
    private String email;

    private String lotName;
    private String lotOwner;
    private Integer startBid;
    private Integer statusId;
    private String saleTerm;
    private String lotBuyer;
    private Integer userBid;

    private String message;
    private String errorMessage;
    private String telegramAccount;

}
