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

    @Query("SELECT new com.starlight.dto.BidDto(l.id as lotId, l.lotName, l.lotOwner, l.startBid, b.username, b.userBid) " +
        "FROM Lot l " +
        "LEFT JOIN Bid b ON l.id = b.lotId " +
        "WHERE l.id = :id ORDER BY l.id DESC")
    List<BidDto> findLotBidsById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO bid (lot_name, lot_id, username, user_bid)" +
            "VALUES (:#{#bid.lotName}, :#{#bid.lotId}, :#{#bid.username}, :#{#bid.userBid})", nativeQuery = true)
    Integer addBid(@Param("bid") Bid bid);


}
