package com.starlight.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Lot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String lotName;
    private String lotOwner;
    private int startBid;
    private int statusId;
    private String lotBuyer;

    @OneToMany
    @JoinColumn(name = "lot_id")
    List<Bid> bids;

}
