package com.starlight.integration.service;

import com.starlight.integration.IntegrationTestBase;
import com.starlight.service.BidService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class BidServiceIT extends IntegrationTestBase {

    private static final Long TEST_LOT_ID = 1L;

    @Autowired
    private BidService bidService;

    @Test
    void listOfLotBidsIsPresentTest() {
        var bidDtoList = bidService.findLotBidsById(TEST_LOT_ID);
        var userBid = bidDtoList.get(0).getUserBid();

        assertAll(() -> assertThat(bidDtoList).isNotEmpty(),
                  () -> assertThat(userBid).isNotNull());

    }

}
