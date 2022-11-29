package com.starlight.auction.service;

import com.starlight.auction.dto.LotDto;
import com.starlight.auction.TestBase;
import com.starlight.auction.model.Bid;
import com.starlight.auction.model.Lot;
import com.starlight.auction.repository.LotRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

class LotServiceTest extends TestBase {

    @InjectMocks
    private LotService lotService;

    @Mock
    private LotRepository lotRepository;
    @Mock
    private ModelMapper modelMapper;

    @Test
    void lotsArePresentTest() {
        var lot = Lot.builder().bids(List.of(new Bid())).build();
        doReturn(List.of(lot)).when(lotRepository).findAll();
        doReturn(new LotDto()).when(modelMapper).map(lot, LotDto.class);

        var actual = lotService.getAll().size();

        assertAll(() -> assertThat(actual).isGreaterThan(0),
                () -> verify(lotRepository).findAll(),
                () -> verify(modelMapper).map(lot, LotDto.class));
    }

}