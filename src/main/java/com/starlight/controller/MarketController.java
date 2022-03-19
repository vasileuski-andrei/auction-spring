package com.starlight.controller;

import com.starlight.model.Lot;
import com.starlight.projection.LotProjection;
import com.starlight.service.LotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/market")
public class MarketController {

    private final LotService lotService;

    @Autowired
    public MarketController(LotService lotService) {
        this.lotService = lotService;
    }

    @GetMapping
    public String getMarketPage(Model model) {
        List<LotProjection> lots = lotService.getAllLot();
        model.addAttribute("lots", lots);
        return "market";
    }
}
