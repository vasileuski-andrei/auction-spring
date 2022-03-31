package com.starlight.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findPasswordByUsernameTest() {
        assertThat(userRepository.findPasswordByUsername("TestUser")).isNotEmpty();
    }

    @Test
    void deleteUserByUsernameTest() {
        assertThat(userRepository.deleteUserByUsername("TestUser")).isEqualTo(1);
    }

    @Test
    void changeUserPasswordByUsernameTest() {
        assertThat(userRepository.changeUserPasswordByUsername("TestUser", "pass")).isEqualTo(1);
    }

}