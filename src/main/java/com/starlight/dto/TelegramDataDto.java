package com.starlight.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TelegramDataDto {

    private String message;
    private String telegramAccount;
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

    private String errorMessage;
}
