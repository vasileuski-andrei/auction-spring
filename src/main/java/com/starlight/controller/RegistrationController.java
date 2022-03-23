package com.starlight.controller;

import com.starlight.dto.UserDto;
import com.starlight.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getRegistrationPage(@ModelAttribute("user") UserDto user) {
        return "registration";
    }

    @PostMapping
    public String createUser(@ModelAttribute("user") @Valid UserDto userDto) {
        userService.create(userDto);
        return "redirect:/index";
    }



}
