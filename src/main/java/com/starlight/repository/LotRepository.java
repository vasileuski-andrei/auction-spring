package com.starlight.repository;

import com.starlight.dto.LotDto;
import com.starlight.model.Lot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LotRepository extends JpaRepository<Lot, Long> {

    @Query("SELECT new com.starlight.dto.LotDto(l.id, l.lotName, l.lotOwner, l.startBid, l.statusId, l.lotBuyer, b.username, MAX(b.userBid)) " +
            "FROM Lot l " +
            "LEFT JOIN Bid b ON l.id = b.lotId " +
            "GROUP BY l.id, b.username " +
            "ORDER BY l.id DESC")
    List<LotDto> findAllLot();

    @Modifying
    @Transactional
    @Query("UPDATE Lot l SET l.statusId = :#{#lot.statusId}, l.lotBuyer = :#{#lot.lotBuyer} " +
            "WHERE l.id = :#{#lot.id}")
    Integer updateLot(@Param("lot") Lot lot);

}
