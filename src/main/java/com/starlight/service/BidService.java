package com.starlight.service;

import com.starlight.dto.BidDto;
import com.starlight.exception.ValidationException;
import com.starlight.model.Bid;
import com.starlight.repository.BidRepository;
import com.starlight.validation.BidValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BidService implements CommonService<BidDto, Long> {

    private final BidRepository bidRepository;
    private final ModelMapper modelMapper;
    private final Map<Long, String> lastBids = new ConcurrentHashMap<>();
    private final BidValidator bidValidator;

    @Autowired
    public BidService(BidRepository bidRepository, ModelMapper modelMapper, BidValidator bidValidator) {
        this.bidRepository = bidRepository;
        this.modelMapper = modelMapper;
        this.bidValidator = bidValidator;
    }

    @Override
    public void create(BidDto model) throws ValidationException {
        bidValidator.validateData(model);

        bidRepository.addBid(convertToBid(model));
        lastBids.put(model.getLotId(), model.getUsername());

    }

    @Override
    public BidDto findById(Long value) {
        return null;
    }

    @Override
    public BidDto update(BidDto model) {
        return null;
    }

    @Override
    public void delete(Long value) {

    }

    @Override
    public List<BidDto> getAll() {
        return null;
    }

    public List<BidDto> findLotBidsById(Long id) {
        return bidRepository.findLotBidsById(id);
    }

    private Bid convertToBid(BidDto bidDto) {
        return modelMapper.map(bidDto, Bid.class);
    }

    public Map<Long, String> getLastBids() {
        return lastBids;
    }

}
