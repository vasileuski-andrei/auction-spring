package com.starlight.projection;

public interface BidProjection {

    Integer getLotId();
    String getLotName();
    String getLotOwner();
    int getStartBid();
    Integer getLastBid();

    String getUsername();
    Integer getUserBid();


}
