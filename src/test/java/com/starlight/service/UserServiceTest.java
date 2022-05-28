package com.starlight.service;

import com.starlight.dto.UserDto;
import com.starlight.model.User;
import com.starlight.repository.UserRepository;
import com.starlight.security.PasswordEncoderConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Transactional
@SpringBootTest
class UserServiceTest {

    private static final String TEST_USER_EMAIL = "test@test.com";
    private static final String TEST_USER_USERNAME = "TestUser";
    private static UserDto userDto;

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoderConfig passwordEncoderConfig;
    @InjectMocks
    private UserService userService;

    @BeforeAll
    static void init() {
        userDto = UserDto.builder()
                .email(TEST_USER_EMAIL)
                .username(TEST_USER_USERNAME)
                .build();
    }

    @Test
    void emailOrUsernameExistTest() {
        doReturn(new User())
                .when(userRepository).findByEmailOrUsername(TEST_USER_EMAIL, TEST_USER_USERNAME);

        var actual = userService.emailOrUsernameExist(userDto);
        assertThat(actual).isTrue();
        verify(userRepository).findByEmailOrUsername(TEST_USER_EMAIL, TEST_USER_USERNAME);
    }

    @Test
    void emailOrUsernameDoesntExistTest() {
        doReturn(null)
                .when(userRepository).findByEmailOrUsername(TEST_USER_EMAIL, TEST_USER_USERNAME);

        var actual = userService.emailOrUsernameExist(userDto);
        assertThat(actual).isFalse();
        verify(userRepository).findByEmailOrUsername(TEST_USER_EMAIL, TEST_USER_USERNAME);
    }

    @Test
    void accountActivationCodeIsPresentTest() {
        doReturn(new User())
                .when(userRepository).findUserByActivationCode(anyString());

        var actual = userService.isActivationCodePresent(anyString());
        assertThat(actual).isTrue();
        verify(userRepository).findUserByActivationCode(anyString());
    }

    @Test
    void accountActivationCodeIsNotPresentTest() {
        doReturn(null)
                .when(userRepository).findUserByActivationCode(anyString());

        var actual = userService.isActivationCodePresent(anyString());
        assertThat(actual).isFalse();
        verify(userRepository).findUserByActivationCode(anyString());
    }

    @Test
    public void userIsPresentTest() {
        doReturn(new User())
                .when(userRepository).findByEmail(anyString());

        var actual = userService.isUserExist(anyString());
        assertThat(actual).isTrue();
        verify(userRepository).findByEmail(anyString());
    }

    @Test
    public void userIsNotPresentTest() {
        doReturn(null)
                .when(userRepository).findByEmail(anyString());

        var actual = userService.isUserExist(anyString());
        assertThat(actual).isFalse();
        verify(userRepository).findByEmail(anyString());
    }

    @AfterAll
    static void destroy() {
        userDto = null;
    }

}