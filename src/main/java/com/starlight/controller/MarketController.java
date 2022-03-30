package com.starlight.controller;

import com.starlight.dto.LotDto;
import com.starlight.service.LotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
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
    public String getMarketPage(@ModelAttribute("lotDto") LotDto lotDto, Model model) {
        List<LotDto> lots = lotService.getAllLot();
        model.addAttribute("lots", lots);
        return "market";
    }


    @PostMapping("/new-lot")
    public String addNewLot(@ModelAttribute("lotDto") @Valid LotDto lotDto, Errors errors, Principal principal) {

        if (errors.hasErrors()) {
            return "market";
        }

        lotDto.setLotOwner(principal.getName());
        lotDto.setStatusId(1);

        lotService.create(lotDto);
        return "redirect:/market";
    }

}
