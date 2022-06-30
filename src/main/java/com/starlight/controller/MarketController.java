package com.starlight.controller;

import com.starlight.dto.LotDto;
import com.starlight.service.LotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/market")
public class MarketController {

    private final LotService lotService;

    @Autowired
    public MarketController(LotService lotService) {
        this.lotService = lotService;
    }

    @GetMapping
    public String getMarketPage(@ModelAttribute("lotDto") LotDto lotDto, Model model,
                                @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("lots", lotService.getAllLot(pageable));
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
