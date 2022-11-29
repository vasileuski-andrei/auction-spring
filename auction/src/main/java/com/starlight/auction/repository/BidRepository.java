package com.starlight.auction.repository;

import com.starlight.auction.dto.BidDto;
import com.starlight.auction.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Long> {

    List<BidDto> findLotBidsById(@Param("id") Long id);

}
