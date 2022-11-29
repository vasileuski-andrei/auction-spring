package com.starlight.auction.integration.controller;

import com.starlight.auction.TestBase;
import com.starlight.auction.integration.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IT
@AutoConfigureMockMvc
@RequiredArgsConstructor
class MarketControllerTest extends TestBase {

    private final MockMvc mockMvc;

    @Test
    void getMarketPageTest() throws Exception {
        mockMvc.perform(get("/market"))

                .andExpect(status().is3xxRedirection());
    }

    @Test
    void addNewLotTest() throws Exception {
        mockMvc.perform(post("/market"))

                .andExpect(status().is3xxRedirection());
    }

}