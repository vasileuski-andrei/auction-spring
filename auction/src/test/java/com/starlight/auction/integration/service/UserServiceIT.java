package com.starlight.auction.integration.service;

import com.starlight.auction.dto.UserDto;
import com.starlight.auction.exception.UserAlreadyExistException;
import com.starlight.auction.exception.ValidationException;
import com.starlight.auction.TestBase;
import com.starlight.auction.integration.annotation.IT;
import com.starlight.auction.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyString;

@IT
@RequiredArgsConstructor
public class UserServiceIT extends TestBase {

    private static final String TEST_USER3 = "TestUser3";
    private static final String TEST_USER_EMAIL = "test@test.com";
    private static final String TEST_USER_EMAIL3 = "test3@test.com";
    private static final String TEST_USER_PASSWORD = "1";
    private static final String TEST_BIRTH_DATE = "1987-03-24";
    private static final String TEST_CODE = "503243233";
    private static UserDto userDto;

    private final UserService userService;

    @BeforeAll
    static void init() {
        userDto = UserDto.builder()
                .username(TEST_USER3)
                .birthDate(LocalDate.parse(TEST_BIRTH_DATE))
                .email(TEST_USER_EMAIL3)
                .password(TEST_USER_PASSWORD)
                .build();
    }

    @Test
    void throwUserAlreadyExistExceptionTest() {
        assertThrows(UserAlreadyExistException.class, () -> userService.create(userDto));
    }

    @Test
    void passwordsMatch() throws ValidationException {
        assertThat(userService.checkUserPassword(TEST_USER3, TEST_USER_PASSWORD)).isTrue();
    }

    @Test
    void throwExceptionWhenPasswordsDoNotMatch() {
        assertThrows(ValidationException.class, () -> userService.checkUserPassword(TEST_USER3, anyString()));
    }

    @Test
    void userIsExist() {
        assertThat(userService.isUserExist(TEST_USER_EMAIL)).isTrue();
    }

    @Test
    void activationCodeIsPresent() {
        assertThat(userService.isActivationCodePresent(TEST_CODE)).isTrue();
    }

    @Test
    void activationCodeIsNotPresent() {
        assertThat(userService.isActivationCodePresent(anyString())).isFalse();
    }

    @Test
    void successfullyDeleteUserTest() throws ValidationException {
        var actual = userService.deleteByUsername(TEST_USER3, TEST_USER_PASSWORD);
        var expected = 1;

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void throwExceptionWhenInputIncorrectPasswordForDeleteUserTest() {
        assertThrows(ValidationException.class, () -> userService.deleteByUsername(anyString(), anyString()));
    }

}
