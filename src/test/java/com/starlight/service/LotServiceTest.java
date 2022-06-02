package com.starlight.service;

import com.starlight.dto.LotDto;
import com.starlight.repository.LotRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@SpringBootTest
class LotServiceTest {

    @InjectMocks
    LotService lotService;

    @Mock
    LotRepository lotRepository;

    @Test
    void lotsArePresentTest() {
        doReturn(List.of(new LotDto())).when(lotRepository).findAllLot();

        var actual = lotService.getAllLot().size();
        var expected = 1;

        assertThat(actual).isEqualTo(expected);
        verify(lotRepository).findAllLot();
    }

}