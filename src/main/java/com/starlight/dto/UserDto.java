package com.starlight.dto;

import com.starlight.validation.annotation.ValidEmail;
import com.starlight.validation.annotation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ValidPassword
public class UserDto {

    private Long id;

    @NotBlank(message = "Username should not be empty")
    @Size(min = 3, max = 15, message = "Username should contain min 3 and max 15 characters")
    private String username;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @ValidEmail
    @NotBlank(message = "Email should not be empty")
    private String email;

    @NotBlank(message = "Password should not be empty")
    @Size(min = 5, max = 30, message = "Password should contain min 5 and max 30 characters")
    private String password;

    @NotBlank(message = "Password confirmation should not be empty")
    private String passwordConfirmation;
}
