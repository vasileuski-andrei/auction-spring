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

    public void create(BidDto bidDto) throws ValidationException {
        bidValidator.validateData(bidDto);

        var bid = convertToBid(bidDto);
        var optionalLot = lotRepository.findById(bidDto.getLotId());
        optionalLot.ifPresent(bid::setLot);

        synchronized (bidRepository) {
            bidRepository.save(bid);
        }

        lastBids.put(bidDto.getLotId(), bidDto.getUsername());
    }

    public List<BidDto> findLotBidsById(Long id) {
        List<BidDto> bidDtoList = null;
        var optionalLot = lotRepository.findById(id);
        if (optionalLot.isPresent()) {
            var lot = optionalLot.get();
            var bids = lot.getBids();
            if (!bids.isEmpty()) {
                bidDtoList = convertToBidDtoList(bids);
                bidDtoList.get(bidDtoList.size()-1).setStartBid(lot.getStartBid());
            } else {
                bidDtoList = List.of(BidDto.builder()
                                .lotName(lot.getLotName())
                                .lotOwner(lot.getLotOwner())
                                .startBid(lot.getStartBid())
                                .lotId(lot.getId()).build());
            }
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
