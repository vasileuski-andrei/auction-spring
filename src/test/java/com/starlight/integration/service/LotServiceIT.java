package com.starlight.integration.service;

import com.starlight.dto.LotDto;
import com.starlight.integration.IntegrationTestBase;
import com.starlight.service.LotService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LotServiceIT extends IntegrationTestBase {

    private static final Long TEST_LOT_ID = 1L;
    private static final String TEST_LOT_NAME = "TestLot2";
    private static final String TEST_LOT_OWNER = "admin";
    private static final Integer TEST_START_BID = 1;
    private static final String TEST_SALE_TERM = "12:00";
    private static final Integer TEST_LOT_STATUS = 1;

    @Autowired
    LotService lotService;

    @Test
    void successfullyCreateNewLotTest() {
        var lotDto = LotDto.builder()
                .lotName(TEST_LOT_NAME)
                .lotOwner(TEST_LOT_OWNER)
                .saleTerm(TEST_SALE_TERM)
                .startBid(TEST_START_BID)
                .statusId(TEST_LOT_STATUS)
                .build();

        assertDoesNotThrow(() -> lotService.create(lotDto));
    }

    @Test
    void changeLotStatusTest() {
        assertDoesNotThrow(() -> lotService.changeLotStatus(TEST_LOT_ID));
    }

    @Test
    void throwExceptionInAttemptChangeLotStatusTest() {
        assertThrows(RuntimeException.class, () -> lotService.changeLotStatus(null));
    }

    @Test
    void lotsArePresentTest() {
        var actual = lotService.getAllLot();

        assertThat(actual).isNotEmpty();
    }

    @Test
    void lotSaleTimeIsExpiredTest() {
        assertThat(lotService.isTheLotStillSale(TEST_LOT_ID)).isFalse();
    }

}