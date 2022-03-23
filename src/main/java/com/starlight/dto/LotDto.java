package com.starlight.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LotDto {

    private Long id;
    private String lotName;
    private String lotOwner;
    private Integer startBid;
    private Integer statusId;
    private String saleTerm;
    private String lotBuyer;
    private Integer userBid;

    public LotDto(Long id, String lotName, String lotOwner, Integer startBid, Integer statusId, String lotBuyer, Integer userBid) {
        this.id = id;
        this.lotName = lotName;
        this.lotOwner = lotOwner;
        this.startBid = startBid;
        this.statusId = statusId;
        this.lotBuyer = lotBuyer;
        this.userBid = userBid;
    }
}
