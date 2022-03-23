package com.starlight.util;

import com.starlight.service.LotService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
@Scope("prototype")
public class LotCountdown implements Runnable {


    private LotService lotService;

    private Long lotId;
    private Thread thread;
    private int saleTimeInSeconds;

    @Autowired
    public LotCountdown(LotService lotService) {
        this.lotService = lotService;
    }

    public void startTimer(Long lotId, int saleTimeInSeconds) {
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
                    lotService.changeLotStatus(lotId);
                }
            }
        }, 0, 1000);

    }

    public String getSaleRemainingTime() {
        if (saleTimeInSeconds == 0) {
            return "-";
        }
        return LocalTime.MIN.plusSeconds(saleTimeInSeconds).toString();
    }

}
