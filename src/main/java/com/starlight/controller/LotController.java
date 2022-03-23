package com.starlight.controller;

import com.starlight.dto.BidDto;
import com.starlight.model.Bid;
import com.starlight.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/lot")
public class LotController {

    private final BidService bidService;

    @Autowired
    public LotController(BidService bidService) {
        this.bidService = bidService;
    }

    @GetMapping("/{id}")
    public String getLotPage(@PathVariable("id") int id, Model model,
                             @ModelAttribute("bid") Bid bid) {
        model.addAttribute("lotBids", bidService.findLotBidsById(id));
        return "lot";
    }

    @PostMapping("/{id}")
    public String addBid(@PathVariable("id") Long id,
                         @RequestParam("lotName") String lotName,
                         @RequestParam("userBid") String userBid) {
        var bidDto = BidDto.builder()
                .lotId(id)
                .lotName(lotName)
                .username("testuser")
                .userBid(Integer.parseInt(userBid))
                .build();
        bidService.create(bidDto);

        return "redirect:/lot/{id}";
    }
}
