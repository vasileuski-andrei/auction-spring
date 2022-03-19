package com.starlight.service;

import com.starlight.model.Lot;
import com.starlight.projection.LotProjection;
import com.starlight.repository.BidRepository;
import com.starlight.repository.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class LotService implements CommonService<Lot, Integer> {

    private final LotRepository lotRepository;

    @Autowired
    public LotService(LotRepository lotRepository) {
        this.lotRepository = lotRepository;
    }

    @Override
    public void create(Lot model) {

    }

    @Override
    public Lot findById(Integer value) {
        return null;
    }

    @Override
    public Lot update(Lot model) {
        return null;
    }

    @Override
    public void delete(Integer value) {

    }

    @Override
    public List<Lot> getAll() {
        return null;
    }

    public List<LotProjection> getAllLot() {
        return lotRepository.findAllLot();
    }

}
