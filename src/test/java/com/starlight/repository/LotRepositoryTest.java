package com.starlight.repository;

import com.starlight.model.Lot;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class LotRepositoryTest {

    @Autowired
    private LotRepository lotRepository;

    @Test
    void findAllLotTest() {
        var listLotDto = lotRepository.findAllLot();
        assertThat(listLotDto).isNotEmpty();
    }

    @Test
    void updateLotTest() {
        var lot = Lot.builder()
                .id(1L)
                .statusId(2)
                .build();
        assertThat(lotRepository.updateLot(lot)).isEqualTo(1);
    }

}