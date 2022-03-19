package com.starlight.controller;

import com.starlight.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/lot")
public class LotController {

    private final BidService bidService;

    @Autowired
    public LotController(BidService bidService) {
        this.bidService = bidService;
    }

    @GetMapping("/{id}")
    public String getLotPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("bids", bidService.findLotBidsById(id));
        return "lot";
    }
}
