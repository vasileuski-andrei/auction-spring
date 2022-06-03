package com.starlight.integration.service;

import com.starlight.integration.IntegrationTestBase;
import com.starlight.service.LotService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class LotServiceIT extends IntegrationTestBase {

    @Autowired
    LotService lotService;

    @Test
    void lotsArePresentTest() {
        var actual = lotService.getAllLot();

        assertThat(actual).isNotEmpty();
    }

}