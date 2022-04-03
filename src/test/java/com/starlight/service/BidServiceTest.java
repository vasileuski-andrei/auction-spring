package com.starlight.service;

import com.starlight.dto.BidDto;
import com.starlight.repository.BidRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;


@Transactional
@SpringBootTest
class BidServiceTest {

    private final Long TEST_LOT_ID = 1L;

    @Mock
    private BidRepository bidRepository;
    @InjectMocks
    private BidService bidService;

    @Test
    void findLotBidsByIdTest() {
        doReturn(List.of(new BidDto()))
                .when(bidRepository).findLotBidsById(TEST_LOT_ID);

        var listBidDto = bidService.findLotBidsById(TEST_LOT_ID);
        assertThat(listBidDto).isNotEmpty();
    }

}