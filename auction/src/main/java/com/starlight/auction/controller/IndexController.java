package com.starlight.auction.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/index")
public class IndexController {

    @GetMapping
    public String getIndexPage() {
        log.info("Open index page");
        return "index";
    }
}
