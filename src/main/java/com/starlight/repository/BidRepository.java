package com.starlight.repository;

import com.starlight.dto.BidDto;
import com.starlight.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Long> {

    List<BidDto> findLotBidsById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO bid (lot_name, lot_id, username, user_bid)" +
            "VALUES (:#{#bid.lotName}, :#{#bid.lotId}, :#{#bid.username}, :#{#bid.userBid})", nativeQuery = true)
    Integer addBid(@Param("bid") Bid bid);

}
