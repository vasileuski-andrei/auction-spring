package com.starlight.telegrambot.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserInfoDto {

    private String message;
    private String telegramAccount;
}
