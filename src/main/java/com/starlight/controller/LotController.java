package com.starlight.controller;

import com.starlight.dto.BidDto;
import com.starlight.exception.ValidationException;
import com.starlight.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/lot")
public class LotController {

    private final BidService bidService;
    private String lotName;
    private String lotOwner;
    private Integer startBid;
    private Integer lastBid;
    private String lastUser;

    @Autowired
    public LotController(BidService bidService) {
        this.bidService = bidService;
    }

    @GetMapping("/{id}")
    public String getLotPage(@PathVariable("id") Long id, Model model) {
        List<BidDto> lotBids= bidService.findLotBidsById(id);
        var lastBidDto = lotBids.get(lotBids.size()-1);

        lotName = lastBidDto.getLotName();
        lotOwner = lastBidDto.getLotOwner();
        startBid = lastBidDto.getStartBid();
        lastBid = lastBidDto.getUserBid();
        lastUser = lastBidDto.getUsername();

        model.addAttribute("lotBids", lotBids);
        return "lot";
    }

    @PostMapping("/{id}")
    public String addBid(@PathVariable("id") Long id,
                         @RequestParam("userBid") String userBid,
                         Principal principal, Model model) {
        var bidDto = BidDto.builder()
                .lotId(id)
                .lotName(lotName)
                .lotOwner(lotOwner)
                .username(principal.getName())
                .userBid(Integer.parseInt(userBid))
                .startBid(startBid)
                .lastBid(lastBid)
                .lastUser(lastUser)
                .build();

        try {
            bidService.create(bidDto);
        } catch (ValidationException e) {
            model.addAttribute("errorMessage", e.getDetail());
            System.out.println(e.getDetail());
        }

        return "redirect:/lot/{id}";
    }

}
