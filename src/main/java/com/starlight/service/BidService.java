package com.starlight.service;

import com.starlight.model.Bid;
import com.starlight.projection.BidProjection;
import com.starlight.projection.LotProjection;
import com.starlight.repository.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidService implements CommonService<Bid, Long> {

    private final BidRepository bidRepository;

    @Autowired
    public BidService(BidRepository bidRepository) {
        this.bidRepository = bidRepository;
    }


    @Override
    public void create(Bid model) {
        bidRepository.save(model);

    }

    @Override
    public Bid findById(Long value) {
        return null;
    }

    @Override
    public Bid update(Bid model) {
        return null;
    }

    @Override
    public void delete(Long value) {

    }

    @Override
    public List<Bid> getAll() {
        return null;
    }

    public List<BidProjection> findLotBidsById(int id) {
        return bidRepository.findLotBidsById(id);
    }
}
