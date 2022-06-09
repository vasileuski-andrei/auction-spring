package com.starlight.service;

import com.starlight.dto.LotDto;
import com.starlight.model.Lot;
import com.starlight.repository.LotRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;


import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.starlight.model.enums.LotStatus.NOT_SOLD;
import static com.starlight.model.enums.LotStatus.SOLD;

@Service
public class LotService implements CommonService<LotDto, Integer> {

    private final ApplicationContext applicationContext;
    private final LotRepository lotRepository;
    private final BidService bidService;
    private final ModelMapper modelMapper;
    private final Map<Integer, String> lastBids = new ConcurrentHashMap<>();
    private final Map<Long, LotCountdown> lotCountdown = new ConcurrentHashMap<>();

    @Autowired
    public LotService(ApplicationContext applicationContext, LotRepository lotRepository, ModelMapper modelMapper, BidService bidService) {
        this.applicationContext = applicationContext;
        this.lotRepository = lotRepository;
        this.bidService = bidService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void create(LotDto lotDto) {
        var savedLot = lotRepository.save(convertToLot(lotDto));
        runLotCountdown(savedLot.getId(), lotDto.getSaleTerm());
    }

    public List<LotDto> getAllLot() {
        var allLotsDto = lotRepository.findAllLot();
        setCurrentSaleTime(allLotsDto);
        return allLotsDto;
    }

    private void setCurrentSaleTime(List<LotDto> allLotsDto) {
        if (lotCountdown.size() != 0) {
            for (LotDto lotDto : allLotsDto) {
                LotCountdown currentLotCountdown = lotCountdown.get(lotDto.getId());

                if (currentLotCountdown != null) {
                    lotDto.setSaleTerm(currentLotCountdown.getSaleRemainingTime());
                }
            }
        }
    }

    public void changeLotStatus(Long lotId) {
        Map<Long, String> lastBids = bidService.getLastBids();
        int lotStatus;
        String lotBuyer = null;

        if (lastBids.size() != 0 && lastBids.get(lotId) != null) {
            lotStatus = SOLD.getId();
            lotBuyer = lastBids.get(lotId);
        } else {
            lotStatus = NOT_SOLD.getId();
        }

        var lot = Lot.builder()
                .id(lotId)
                .statusId(lotStatus)
                .lotBuyer(lotBuyer)
                .build();

        lotRepository.updateLot(lot);
        lotCountdown.remove(lotId);
        lastBids.remove(lotId);
    }

    public boolean isTheLotStillSale(Long id) {
        return lotCountdown.containsKey(id);
    }

    private void runLotCountdown(Long lotId, String saleTerm) {
        LotCountdown currentLotCountdown = applicationContext.getBean(LotCountdown.class);
        lotCountdown.put(lotId, currentLotCountdown);
        currentLotCountdown.startTimer(lotId, LocalTime.parse(saleTerm).toSecondOfDay());
    }

    private Lot convertToLot(LotDto lotDto) {
        return modelMapper.map(lotDto, Lot.class);
    }

}
