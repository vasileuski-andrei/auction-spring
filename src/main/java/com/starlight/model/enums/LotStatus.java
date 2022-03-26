package com.starlight.model.enums;

public enum LotStatus {

    SELL(1),
    SOLD(2),
    NOT_SOLD(3);

    final private int id;

    LotStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
