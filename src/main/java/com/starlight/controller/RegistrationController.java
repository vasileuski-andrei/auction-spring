package com.starlight.controller;

import com.starlight.dto.CaptchaResponseDto;
import com.starlight.dto.UserDto;
import com.starlight.exception.UserAlreadyExistException;
import com.starlight.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collections;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private static final String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    private final UserService userService;
    private RestTemplate restTemplate;
    @Value("${recaptcha.secret}")
    private String recaptchaSecret;

    @Autowired
    public RegistrationController(UserService userService, RestTemplate restTemplate) {
        this.userService = userService;
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public String getRegistrationPage(@ModelAttribute("user") UserDto user) {
        return "registration";
    }

    @PostMapping
    public String createUser(@ModelAttribute("user") @Valid UserDto userDto,
                             Errors errors, @RequestParam("g-recaptcha-response") String captchaResponse,
                             Model model) {

        String url = String.format(CAPTCHA_URL, recaptchaSecret, captchaResponse);
        CaptchaResponseDto captchaResponseDto = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);

        if (errors.hasErrors()) {
            return "registration";
        }

        if (captchaResponseDto.isSuccess()) {
            try {
                userService.create(userDto);
                model.addAttribute("message", "Letter with an activation code has been sent to your email");
            } catch (UserAlreadyExistException e) {
                model.addAttribute("errorMessage", e.getDetail());
                return "registration";
            }
        } else {
            model.addAttribute("errorMessage", "Please, fill captcha");
            return "registration";
        }

        return "/login";
    }

    @GetMapping("/activate/{code}")
    public String accountActivate(@PathVariable String code, Model model) {
        if (userService.isUserAccountActivated(code)) {
            model.addAttribute("successMessage", "Your account has been successfully activated");
        } else {
            model.addAttribute("unsuccessMessage", "Activation code is not found");
        }

        return "login";
    }



}
