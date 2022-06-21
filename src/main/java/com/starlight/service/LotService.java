package com.starlight.service;

import com.starlight.dto.LotDto;
import com.starlight.model.Bid;
import com.starlight.model.Lot;
import com.starlight.repository.LotRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.starlight.model.enums.LotStatus.NOT_SOLD;
import static com.starlight.model.enums.LotStatus.SOLD;

@Service
public class LotService {

    private final ApplicationContext applicationContext;
    private final UserService userService;
    private final LotRepository lotRepository;
    private final BidService bidService;
    private final ModelMapper modelMapper;
    private final Map<Integer, String> lastBids = new ConcurrentHashMap<>();
    private final Map<Long, LotCountdown> lotCountdown = new ConcurrentHashMap<>();

    @Autowired
    public LotService(ApplicationContext applicationContext, UserService userService,LotRepository lotRepository, ModelMapper modelMapper, BidService bidService) {
        this.applicationContext = applicationContext;
        this.userService = userService;
        this.lotRepository = lotRepository;
        this.bidService = bidService;
        this.modelMapper = modelMapper;
    }

    public void create(LotDto lotDto) {
        var lot = convertToLot(lotDto);
        lot.setUser(userService.getUserByUsername(lotDto.getLotOwner()));
        var savedLot = lotRepository.save(lot);
        runLotCountdown(savedLot.getId(), lotDto.getSaleTerm());
    }

    public List<LotDto> getAllLot() {
        var allLots = lotRepository.findAll();
        var allLotsDto = convertToLotDtoList(allLots);
        setCurrentSaleTime(allLotsDto);
        setMaxUserBid(allLots, allLotsDto);

        return allLotsDto;
    }

    private void setMaxUserBid(List<Lot> allLots, List<LotDto> allLotsDto) {
        for (int i = 0; i < allLots.size(); i++) {
            var bids = allLots.get(i).getBids();
            if (!bids.isEmpty()) {
                var sortedBids = bids.stream().sorted(Comparator.comparing(Bid::getUserBid).reversed()).toList();

                allLotsDto.get(i).setUserBid(sortedBids.get(0).getUserBid());
                allLotsDto.get(i).setUsername(sortedBids.get(0).getUsername());
            }
        }
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

    private List<LotDto> convertToLotDtoList(List<Lot> lots) {
        List<LotDto> lotDtoList = new ArrayList<>();
        for (Lot lot : lots) {
            lotDtoList.add(modelMapper.map(lot, LotDto.class));
        }
        return lotDtoList;
    }

}
