package com.starlight.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LotDto {

    private Integer id;
    private String lotName;
    private String lotOwner;
    private int startBid;
    private int statusId;
    private String saleTerm;
    private String lotBuyer;
}
