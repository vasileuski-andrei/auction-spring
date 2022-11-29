package com.starlight.auction.integration.service;

import com.starlight.auction.dto.BidDto;
import com.starlight.auction.exception.ValidationException;
import com.starlight.auction.TestBase;
import com.starlight.auction.integration.annotation.IT;
import com.starlight.auction.service.BidService;
import com.starlight.auction.service.LotService;
import com.starlight.auction.service.LotCountdown;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
public class BidServiceIT extends TestBase {

    private static final Long TEST_LOT_ID = 1L;
    private static final String TEST_USER = "TestUser2";
    private static final Integer TEST_USER_BID = 50;
    private static final Integer TEST_USER_BID_NEGATIVE = -50;
    private static final Integer TEST_START_BID = 1;
    private static BidDto bidDto;

    private final BidService bidService;
    private final LotService lotService;

    @BeforeAll
    static void init() {
        bidDto = BidDto.builder()
                .lotName("TestLot")
                .lotId(TEST_LOT_ID)
                .username(TEST_USER)
                .startBid(TEST_START_BID)
                .build();
    }

    @Test
    void listOfLotBidsIsPresentTest() {
        var bidDtoList = bidService.findLotBidsById(TEST_LOT_ID);
        var userBid = bidDtoList.get(0).getUserBid();

        assertAll(() -> assertThat(bidDtoList).isNotEmpty(),
                  () -> assertThat(userBid).isNotNull());
    }

    @Test
    void successfullyAddBidTest() throws NoSuchFieldException, IllegalAccessException {
        startLotCountdownForLot();
        bidDto.setUserBid(TEST_USER_BID);

        assertDoesNotThrow(() -> bidService.create(bidDto));
    }

    @Test
    void throwExceptionIfValidationIsNotPassedTest() throws NoSuchFieldException, IllegalAccessException {
        startLotCountdownForLot();
        bidDto.setUserBid(TEST_USER_BID_NEGATIVE);

        assertThrows(ValidationException.class, () -> bidService.create(bidDto));
    }

    private void startLotCountdownForLot() throws NoSuchFieldException, IllegalAccessException {
        var declaredField = lotService.getClass().getDeclaredField("lotCountdown");
        declaredField.setAccessible(true);
        Map<Long, LotCountdown> lotCountdown = (Map)declaredField.get(lotService);
        lotCountdown.put(TEST_LOT_ID, new LotCountdown());
    }

    @AfterAll
    static void destroy() {
        bidDto = null;
    }

}
