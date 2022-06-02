package com.starlight.service;

import com.starlight.dto.BidDto;
import com.starlight.repository.BidRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;


@SpringBootTest
class BidServiceTest {

    private static final Long TEST_LOT_ID = 1L;

    @InjectMocks
    private BidService bidService;

    @Mock
    private BidRepository bidRepository;

    @Test
    void listOfLotBidsIsPresentTest() {
        doReturn(List.of(new BidDto())).when(bidRepository).findLotBidsById(TEST_LOT_ID);

        var listBidDto = bidService.findLotBidsById(TEST_LOT_ID);

        assertThat(listBidDto).isNotEmpty();
        verify(bidRepository).findLotBidsById(TEST_LOT_ID);
    }

}