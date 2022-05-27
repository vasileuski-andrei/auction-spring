package com.starlight.service;

import com.starlight.security.PasswordEncoderConfig;
import com.starlight.security.SecurityConfig;
import com.starlight.dto.UserDto;
import com.starlight.exception.ValidationException;
import com.starlight.model.User;
import com.starlight.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@Transactional
@SpringBootTest
class UserServiceTest {

    private final String TEST_USER_EMAIL = "test@test.com";
    private final String TEST_USER_USERNAME = "TestUser";
    private final String TEST_USER_PASSWORD_DB = "$2a$12$o3lI0qeOG9h.B7RFkoUDLOY9VJa/Ih5Hl1Aa34.ATNb1tTrogOCnu";
    private final String TEST_USER_PASSWORD = "1";
    private final String TEST_USER_CODE = "code";

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoderConfig passwordEncoderConfig;
    @InjectMocks
    private UserService userService;

    @Test
    void emailOrUsernameExistTest() {
        doReturn(new User())
                .when(userRepository).findByEmailOrUsername(TEST_USER_EMAIL, TEST_USER_USERNAME);

        var userDto = UserDto.builder()
                .email(TEST_USER_EMAIL)
                .username(TEST_USER_USERNAME)
                .build();

        var isUserExist = userService.emailOrUsernameExist(userDto);
        assertThat(isUserExist).isTrue();
    }

    @Test
    void emailOrUsernameDoesntExistTest() {
        doReturn(null)
                .when(userRepository).findByEmailOrUsername(TEST_USER_EMAIL, TEST_USER_USERNAME);

        var userDto = UserDto.builder()
                .email(TEST_USER_EMAIL)
                .username(TEST_USER_USERNAME)
                .build();

        var isUserExist = userService.emailOrUsernameExist(userDto);
        assertThat(isUserExist).isFalse();
    }

    @Test
    void accountActivatedCodePresentTest() {
        doReturn(new User())
                .when(userRepository).findUserByActivationCode(TEST_USER_CODE);

        var isActivationCodePresent = userService.isUserAccountActivated(TEST_USER_CODE);
        assertThat(isActivationCodePresent).isTrue();
    }

    @Test
    void accountActivatedCodeNotPresentTest() {
        doReturn(null)
                .when(userRepository).findUserByActivationCode(TEST_USER_CODE);

        var isActivationCodePresent = userService.isUserAccountActivated(TEST_USER_CODE);
        assertThat(isActivationCodePresent).isFalse();
    }

    @Test
    void userPasswordIsEqualTest() throws ValidationException {
        doReturn(TEST_USER_PASSWORD_DB)
                .when(userRepository).findPasswordByUsername(TEST_USER_USERNAME);
        doReturn(new BCryptPasswordEncoder(12))
                .when(passwordEncoderConfig).passwordEncoder();

        var arePasswordsEqual = userService.checkUserPassword(TEST_USER_USERNAME, TEST_USER_PASSWORD);
        assertThat(arePasswordsEqual).isTrue();
    }

}