package com.starlight.validation;

import com.starlight.dto.BidDto;
import com.starlight.exception.ValidationException;
import com.starlight.service.LotService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Getter
@Component
public class BidValidator {

    private BidDto bidDto;
    private final LotService lotService;

    @Autowired
    public BidValidator(@Lazy LotService lotService) {
        this.lotService = lotService;
    }

    public void validateData(BidDto bidDto) throws ValidationException {
        this.bidDto = bidDto;

        checkBidOnOwner();
        checkLotSaleTime();
        checkDoubleBidInARow();
        checkInputCharacterInBidField();

    }

    private void checkBidOnOwner() throws ValidationException {
        if (bidDto.getUsername().equals(bidDto.getLotOwner())) {
            throw new ValidationException("It's your lot. You can't place a bid.");
        }
    }

    private void checkLotSaleTime() throws ValidationException {
        if (!lotService.isTheLotStillSale(bidDto.getLotId())) {
            throw new ValidationException("You can't place a bid after elapsed sale time");
        }

    }

    private void checkDoubleBidInARow() throws ValidationException {
        if (bidDto.getUsername().equals(bidDto.getLastUser())) {
            throw new ValidationException("You can't place two bids in a row");
        }

    }

    private void checkInputCharacterInBidField() throws ValidationException {
        var userBid = bidDto.getUserBid();

        if (!String.valueOf(userBid).matches("[0-9]+")) {
            throw new ValidationException("You can use only integer numbers");
        }

        if (userBid <= bidDto.getStartBid() || bidDto.getLastBid() != null && userBid <= bidDto.getLastBid()) {
            throw new ValidationException("Your bid should be greater than current bid");
        }
        System.out.println();
    }



}
