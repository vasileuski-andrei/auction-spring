package com.starlight.auction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BidDto {

    private Long lotId;
    private String lotName;
    private String lotOwner;
    private Integer startBid;
    private String username;
    private Integer userBid;
    private Integer lastBid;
    private String lastUser;

    public BidDto(Long lotId, String lotName, String lotOwner, Integer startBid, String username, Integer userBid) {
        this.lotId = lotId;
        this.lotName = lotName;
        this.lotOwner = lotOwner;
        this.startBid = startBid;
        this.username = username;
        this.userBid = userBid;
    }
}
