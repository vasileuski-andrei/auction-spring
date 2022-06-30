package com.starlight.repository;

import com.starlight.model.Lot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface LotRepository extends JpaRepository<Lot, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Lot l SET l.statusId = :#{#lot.statusId}, l.lotBuyer = :#{#lot.lotBuyer} " +
            "WHERE l.id = :#{#lot.id}")
    Integer updateLot(@Param("lot") Lot lot);

    Page<Lot> findAll(Pageable pageable);

    List<Lot> findAll();

    Optional<Lot> findById(Long id);

}
