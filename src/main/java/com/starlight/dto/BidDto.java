package com.starlight.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BidDto {

    private Integer id;
    private String lotName;
    private Long lotId;
    private String username;
    private int userBid;
}
