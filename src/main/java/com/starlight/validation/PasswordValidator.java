package com.starlight.validation;

import com.starlight.dto.UserDto;
import com.starlight.validation.annotation.ValidPassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, Object> {

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        UserDto userDto = (UserDto) obj;
        System.out.println("UserDto " + userDto);
        return userDto.getPassword().equals(userDto.getPasswordConfirmation());
    }
}
