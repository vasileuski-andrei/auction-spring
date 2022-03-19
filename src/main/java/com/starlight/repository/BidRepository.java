package com.starlight.repository;

import com.starlight.model.Bid;
import com.starlight.projection.BidProjection;
import com.starlight.projection.LotProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Long> {

    @Query(
            value = "SELECT l.id as lotId, " +
                    "l.lot_name as lotName, " +
                    "l.lot_owner as lotOwner, " +
                    "l.start_bid as startBid, " +
                    "b.username as username, " +
                    "b.user_bid as userBid\n" +
                    "FROM lot l LEFT JOIN bid b ON l.id = b.lot_id\n" +
                    "WHERE l.id = :id", nativeQuery = true)
    List<BidProjection> findLotBidsById(@Param("id") int id);



}
