package com.starlight.repository;

import com.starlight.model.Lot;
import com.starlight.projection.LotProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LotRepository extends JpaRepository<Lot, Integer> {

    @Query(
            value = "SELECT l.id as id, " +
                    "l.lot_name as lotName, " +
                    "l.lot_owner as lotOwner, " +
                    "l.start_bid as startBid, " +
                    "l.status_id as statusId, " +
                    "l.lot_buyer as lotBuyer, " +
                    "MAX(b.user_bid) as lastBid\n" +
                    "FROM lot l LEFT JOIN bid b\n" +
                    "ON l.id = b.lot_id\n" +
                    "GROUP BY l.id", nativeQuery = true)
    List<LotProjection> findAllLot();



}
