package com.starlight.service;

import com.starlight.dto.LotDto;
import com.starlight.model.Lot;
import com.starlight.projection.LotProjection;
import com.starlight.repository.LotRepository;
import com.starlight.util.LotCountdown;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LotService implements CommonService<LotDto, Integer> {

    private final LotRepository lotRepository;
    private ModelMapper modelMapper;
    private static final Map<Integer, LotCountdown> lotCountdown = new ConcurrentHashMap<>();

    @Autowired
    public LotService(LotRepository lotRepository, ModelMapper modelMapper) {
        this.lotRepository = lotRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void create(LotDto lotDto) {
        var savedLot = lotRepository.save(convertToLot(lotDto));
        runLotCountdown(savedLot.getId(), lotDto.getSaleTerm());
    }

    @Override
    public LotDto findById(Integer value) {
        return null;
    }

    @Override
    public LotDto update(LotDto model) {
        return null;
    }

    @Override
    public void delete(Integer value) {

    }

    @Override
    public List<LotDto> getAll() {
        return null;
    }

    public List<LotProjection> getAllLot() {
        return lotRepository.findAllLot();
    }

    private void runLotCountdown(int lotId, String saleTerm) {
        lotCountdown.put(lotId, new LotCountdown(lotId, LocalTime.parse(saleTerm).toSecondOfDay()));
    }

    private Lot convertToLot(LotDto lotDto) {
        return modelMapper.map(lotDto, Lot.class);
    }

}
