package com.starlight.repository;

import com.starlight.dto.BidDto;
import com.starlight.model.Bid;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class BidRepositoryTest {

    @Autowired
    private BidRepository bidRepository;

    @Test
    void findLotBidsByIdTest() {
        List<BidDto> listBidDto = bidRepository.findLotBidsById(1L);
        assertThat(listBidDto).isNotEmpty();
    }

    @Test
    void addBidTest() {
        var bid = Bid.builder()
                .lotName("TestLot")
                .lotId(1L)
                .username("TestUser")
                .userBid(2)
                .build();
        assertThat(bidRepository.addBid(bid)).isEqualTo(1);
    }
}