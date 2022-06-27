package com.starlight.integration.controller;

import com.starlight.TestBase;
import com.starlight.integration.annotation.IT;
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
class IndexControllerTest extends TestBase {

    private final MockMvc mockMvc;

    @Test
    void getIndexPageTest() throws Exception {
        mockMvc.perform(get("/index"))

                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name("index")
                );
    }
}