package com.starlight.auction.integration.controller;

import com.starlight.auction.TestBase;
import com.starlight.auction.integration.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@IT
@AutoConfigureMockMvc
@RequiredArgsConstructor
class RegistrationControllerTest extends TestBase {

    private final MockMvc mockMvc;

    @Test
    void getRegistrationPageTest() throws Exception {
        mockMvc.perform(get("/registration"))

                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name("registration")
                );

    }

}