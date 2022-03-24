package com.starlight.controller;

import com.starlight.dto.UserDto;
import com.starlight.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
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
    public String createUser(@ModelAttribute("user") @Valid UserDto userDto, Errors errors) {
        for (ObjectError error : errors.getAllErrors()) {
            System.out.println(error.getObjectName());
            System.out.println(error.getDefaultMessage());
//            System.out.println("test field");
        }
        if (!errors.hasErrors()) {
            userService.create(userDto);
        } else {
            return "registration";
        }

        return "redirect:/index";
    }



}
