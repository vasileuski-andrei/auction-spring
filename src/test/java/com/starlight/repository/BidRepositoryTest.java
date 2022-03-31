package com.starlight.repository;

import com.starlight.dto.BidDto;
import com.starlight.model.Bid;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class BidRepositoryTest {

    @Autowired
    private BidRepository bidRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findLotBidsByIdTest() {
        List<BidDto> list = bidRepository.findLotBidsById(1L);
        System.out.println(list);
        assertFalse(list.isEmpty());
    }

    @Test
    void addBidTest() {
        var bid = Bid.builder()
                .lotName("TestLot")
                .lotId(1L)
                .username("TestUser")
                .userBid(2)
                .build();
        assertEquals(1, bidRepository.addBid(bid));
    }
}