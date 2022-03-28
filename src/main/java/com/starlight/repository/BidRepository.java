package com.starlight.repository;

import com.starlight.dto.BidDto;
import com.starlight.model.Bid;
import com.starlight.model.Lot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Long> {

    @Query("SELECT new com.starlight.dto.BidDto(l.id as lotId, l.lotName, l.lotOwner, l.startBid, b.username, b.userBid) " +
        "FROM Lot l " +
        "LEFT JOIN Bid b ON l.id = b.lotId " +
        "WHERE l.id = :id ORDER BY l.id DESC")
    List<BidDto> findLotBidsById(@Param("id") Long id);

    @Query(value = "INSERT INTO Bid (lotName, lotId, username, userBid)" +
            "VALUES (:#{#bid.lotName}, :#{#bid.lotId}, :#{#bid.username}, :#{#bid.userBid})", nativeQuery = true)
    void addData(@Param("bid") Bid bid);


    @Query(value = "SELECT MAX(b.user_bid) as usBid, b.username " +
            "FROM bid b " +
            "WHERE b.lot_id = ?1 " +
            "GROUP BY b.username " +
            "LIMIT 1", nativeQuery = true)
    String findLastLotBIdById(@Param("id") Long id);


}
