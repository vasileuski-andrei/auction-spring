package com.starlight.auction.validation;

import com.starlight.auction.dto.UserDto;
import com.starlight.auction.validation.annotation.ValidPassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, Object> {

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        UserDto userDto = (UserDto) obj;
        return userDto.getPassword().equals(userDto.getPasswordConfirmation());
    }
}
