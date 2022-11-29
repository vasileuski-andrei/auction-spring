package com.starlight.auction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LotDto {

    private Long id;
    @Size(min = 2, max = 50, message = "Lot name should contain min 3 and max 50 characters")
    private String lotName;
    private String lotOwner;
    @NotNull(message = "Start bid should not be empty")
    @Positive(message = "Start bid should be not negative integer number")
    @Digits(integer=6, fraction=0, message = "Bid should be less than 1 000 000")
    private Integer startBid;
    private Integer statusId;
    @NotBlank(message = "Sale term should not be empty")
    @DateTimeFormat(pattern = "HH-mm")
    private String saleTerm;
    private String lotBuyer;
    private Integer userBid;
    private String username;

    public LotDto(Long id, String lotName, String lotOwner, Integer startBid, Integer statusId, String lotBuyer, String username, Integer userBid) {
        this.id = id;
        this.lotName = lotName;
        this.lotOwner = lotOwner;
        this.startBid = startBid;
        this.statusId = statusId;
        this.lotBuyer = lotBuyer;
        this.username = username;
        this.userBid = userBid;
    }
}
