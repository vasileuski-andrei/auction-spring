package com.starlight.integration.controller;

import com.starlight.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class MarketControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    void getMarketPageTest() throws Exception {
        mockMvc.perform(get("/market"))

                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name("market")

                );
    }


}