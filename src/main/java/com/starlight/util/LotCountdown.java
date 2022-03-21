package com.starlight.util;

import com.starlight.service.LotService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Timer;
import java.util.TimerTask;

@Data
public class LotCountdown implements Runnable {
    private int lotId;
    private Thread thread;
    private int saleTimeInSeconds;

    public LotCountdown(int lotId, int saleTimeInSeconds) {
        this.lotId = lotId;
        this.saleTimeInSeconds = saleTimeInSeconds;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                saleTimeInSeconds--;

                if (saleTimeInSeconds == 0) {
                    timer.cancel();
//                    lotService.updateLot(lotId);
                }
            }
        }, 0, 1000);

    }

//    public String getSaleRemainingTime() {
//        if (saleTimeInSeconds == null) {
//            return "-";
//        }
//        return LocalTime.MIN.plusSeconds(saleTimeInSeconds).toString();
//    }

}
