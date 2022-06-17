package com.starlight.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoDto {

    private String message;
    private String telegramAccount;
}