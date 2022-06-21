package com.starlight.telegrambot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {

    private String message;
    private String telegramAccount;
    private Long id;
    private String username;
    private String birthDate;
    private String email;
}
