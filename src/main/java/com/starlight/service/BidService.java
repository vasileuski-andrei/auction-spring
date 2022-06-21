package com.starlight.service;

import com.starlight.dto.BidDto;
import com.starlight.exception.ValidationException;
import com.starlight.model.Bid;
import com.starlight.repository.BidRepository;
import com.starlight.repository.LotRepository;
import com.starlight.validation.BidValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BidService {

    private final BidRepository bidRepository;
    private final LotRepository lotRepository;
    private final ModelMapper modelMapper;
    private final Map<Long, String> lastBids = new ConcurrentHashMap<>();
    private final BidValidator bidValidator;

    @Autowired
    public BidService(BidRepository bidRepository, LotRepository lotRepository,ModelMapper modelMapper, BidValidator bidValidator) {
        this.lotRepository = lotRepository;
        this.bidRepository = bidRepository;
        this.modelMapper = modelMapper;
        this.bidValidator = bidValidator;
    }

    public void create(BidDto model) throws ValidationException {
        bidValidator.validateData(model);

        synchronized (bidRepository) {
            bidRepository.addBid(convertToBid(model));
        }

        lastBids.put(model.getLotId(), model.getUsername());
    }

    public List<BidDto> findLotBidsById(Long id) {
        List<BidDto> bidDtoList = null;
        var lot = lotRepository.findById(id);
        if (lot.isPresent()) {
            var bids = lot.get().getBids();
            bidDtoList = convertToBidDtoList(bids);
            bidDtoList.get(bidDtoList.size()-1).setStartBid(lot.get().getStartBid());
        }

        return bidDtoList;
    }

    public Map<Long, String> getLastBids() {
        return lastBids;
    }

    private Bid convertToBid(BidDto bidDto) {
        return modelMapper.map(bidDto, Bid.class);
    }

    private List<BidDto> convertToBidDtoList(List<Bid> bids) {
        List<BidDto> bidDtoList = new ArrayList<>();
        for (Bid bid : bids) {
            bidDtoList.add(modelMapper.map(bid, BidDto.class));
        }
        return bidDtoList;
    }


}
