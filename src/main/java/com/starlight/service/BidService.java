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
        var lotId = model.getLotId();
        bidValidator.validateData(model);

//        bidRepository.save(convertToBid(model));
        bidRepository.addData(convertToBid(model));
        lastBids.put(lotId, model.getUsername());

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
        var bidDto = bidRepository.findLotBidsById(id);
        var lotInfo = bidDto.get(0);

        if (lotInfo.getUsername() != null) {
            String[] lastBidInfo = bidRepository.findLastLotBIdById(id).split(",");
            System.out.println();
            lotInfo.setLastBid(Integer.parseInt(lastBidInfo[0]));
            lotInfo.setLastUser(lastBidInfo[1]);
        }

        System.out.println(bidDto.toString());

        return bidDto;
    }

    private Bid convertToBid(BidDto bidDto) {
        return modelMapper.map(bidDto, Bid.class);
    }

    public Map<Long, String> getLastBids() {
        return lastBids;
    }

}
