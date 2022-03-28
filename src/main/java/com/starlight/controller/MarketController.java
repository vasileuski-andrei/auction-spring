package com.starlight.controller;

import com.starlight.dto.LotDto;
import com.starlight.service.LotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
        List<LotDto> lots = lotService.getAllLot();
        model.addAttribute("lots", lots);
        return "market";
    }

    @PostMapping("/new-lot")
    public String addNewLot(@RequestParam("lotName") String lotName,
                            @RequestParam("bid") String bid,
                            @RequestParam("term") String term, Principal principal) {

        System.out.println();

        var lotDto = LotDto.builder()
                .lotName(lotName)
                .startBid(Integer.parseInt(bid))
                .lotOwner(principal.getName())
                .statusId(1)
                .saleTerm(term)
                .build();
        lotService.create(lotDto);
        return "redirect:/market";
    }

}
