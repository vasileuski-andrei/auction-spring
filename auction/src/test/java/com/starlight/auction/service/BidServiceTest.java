package com.starlight.auction.service;

import com.starlight.auction.TestBase;
import com.starlight.auction.dto.BidDto;
import com.starlight.auction.model.Bid;
import com.starlight.auction.model.Lot;
import com.starlight.auction.repository.LotRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

class BidServiceTest extends TestBase {

    private static final Long TEST_LOT_ID = 1L;

    @InjectMocks
    private BidService bidService;

    @Mock
    private LotRepository lotRepository;
    @Mock
    private ModelMapper modelMapper;

    @Test
    void listOfLotBidsIsPresentTest() {
        var lot = Lot.builder().bids(List.of(new Bid())).build();
        doReturn(Optional.of(lot)).when(lotRepository).findById(anyLong());
        doReturn(new BidDto()).when(modelMapper).map(new Bid(), BidDto.class);

        var actual = bidService.findLotBidsById(TEST_LOT_ID);

        assertAll(() -> assertThat(actual).isNotEmpty(),
                  () -> verify(lotRepository).findById(TEST_LOT_ID),
                  () -> verify(modelMapper).map(new Bid(), BidDto.class));

    }

}