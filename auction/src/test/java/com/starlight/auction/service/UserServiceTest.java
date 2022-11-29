package com.starlight.auction.service;

import com.starlight.auction.TestBase;
import com.starlight.auction.dto.UserDto;
import com.starlight.auction.model.User;
import com.starlight.auction.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UserServiceTest extends TestBase {

    private static final String TEST_USER_EMAIL = "test@test.com";
    private static final String TEST_USER_USERNAME = "TestUser";
    private static UserDto userDto;

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeAll
    static void init() {
        userDto = UserDto.builder()
                .email(TEST_USER_EMAIL)
                .username(TEST_USER_USERNAME)
                .build();
    }

    @Test
    void emailOrUsernameExistTest() {
        doReturn(new User()).when(userRepository).findByEmailOrUsername(TEST_USER_EMAIL, TEST_USER_USERNAME);

        var actual = userService.emailOrUsernameExist(userDto);

        assertThat(actual).isTrue();
        verify(userRepository).findByEmailOrUsername(TEST_USER_EMAIL, TEST_USER_USERNAME);
    }

    @Test
    void emailOrUsernameDoesntExistTest() {
        doReturn(null).when(userRepository).findByEmailOrUsername(TEST_USER_EMAIL, TEST_USER_USERNAME);

        var actual = userService.emailOrUsernameExist(userDto);

        assertThat(actual).isFalse();
        verify(userRepository).findByEmailOrUsername(TEST_USER_EMAIL, TEST_USER_USERNAME);
    }

    @Test
    void accountActivationCodeIsPresentTest() {
        doReturn(new User()).when(userRepository).findUserByActivationCode(anyString());

        var actual = userService.isActivationCodePresent(anyString());

        assertThat(actual).isTrue();
        verify(userRepository).findUserByActivationCode(anyString());
    }

    @Test
    void accountActivationCodeIsNotPresentTest() {
        doReturn(null).when(userRepository).findUserByActivationCode(anyString());

        var actual = userService.isActivationCodePresent(anyString());

        assertThat(actual).isFalse();
        verify(userRepository).findUserByActivationCode(anyString());
    }

    @Test
    public void userIsPresentTest() {
        doReturn(new User()).when(userRepository).findByEmail(anyString());

        var actual = userService.isUserExist(anyString());

        assertThat(actual).isTrue();
        verify(userRepository).findByEmail(anyString());
    }

    @Test
    public void userIsNotPresentTest() {
        doReturn(null).when(userRepository).findByEmail(anyString());

        var actual = userService.isUserExist(anyString());

        assertThat(actual).isFalse();
        verify(userRepository).findByEmail(anyString());
    }

    @AfterAll
    static void destroy() {
        userDto = null;
    }

}