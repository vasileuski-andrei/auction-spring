package com.starlight.integration.service;

import com.starlight.exception.ValidationException;
import com.starlight.integration.IntegrationTestBase;
import com.starlight.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyString;

public class UserServiceIT extends IntegrationTestBase {

    private static final String TEST_USER = "TestUser2";
    private static final String TEST_USER_EMAIL = "test@test.com";
    private static final String TEST_USER_PASSWORD = "1";
    private static final String TEST_CODE = "503243233";

    @Autowired
    private UserService userService;

    @Test
    void passwordsMatch() throws ValidationException {
        assertThat(userService.checkUserPassword(TEST_USER, TEST_USER_PASSWORD)).isTrue();
    }

    @Test
    void throwExceptionWhenPasswordsDoNotMatch() {
        assertThrows(ValidationException.class, () -> userService.checkUserPassword(TEST_USER, anyString()));
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

}
