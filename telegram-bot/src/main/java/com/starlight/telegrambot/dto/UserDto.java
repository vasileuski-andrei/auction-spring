package com.starlight.telegrambot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String username;
    private LocalDate birthDate;
    private String email;
    private String password;
    private String role;
    private String userStatus;
    private String activationCode;
    private String telegramAccount;

}
