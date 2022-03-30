package com.starlight.controller;

import com.starlight.dto.UserDto;
import com.starlight.exception.UserAlreadyExistException;
import com.starlight.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    public String createUser(@ModelAttribute("user") UserDto userDto,
                             Errors errors, Model model) {
        if (errors.hasErrors()) {
            return "registration";
        }

        try {
            userService.create(userDto);
            model.addAttribute("message", "Letter with an activation code has been sent to your email");
        } catch (UserAlreadyExistException e) {
            model.addAttribute("errorMessage", e.getDetail());
            return "registration";
        }

        return "/login";
    }

    @GetMapping("/activate/{code}")
    public String accountActivate(@PathVariable String code, Model model) {
        if (userService.isUserAccountActivated(code)) {
            model.addAttribute("successMessage", "Your account successfully activated");
        } else {
            model.addAttribute("unsuccessMessage", "Activation code is not found");
        }

        return "login";
    }



}
