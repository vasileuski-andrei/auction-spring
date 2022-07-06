package com.starlight.controller;

import com.starlight.dto.CaptchaResponseDto;
import com.starlight.dto.UserDto;
import com.starlight.exception.UserAlreadyExistException;
import com.starlight.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collections;

@Slf4j
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
        log.info("Open registration page");
        return "registration";
    }

    @PostMapping
    public String createUser(@ModelAttribute("user") @Valid UserDto userDto,
                             Errors errors, @RequestParam("g-recaptcha-response") String captchaResponse,
                             Model model) {

        String url = String.format(CAPTCHA_URL, recaptchaSecret, captchaResponse);
        CaptchaResponseDto captchaResponseDto = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);

        if (errors.hasErrors()) {
            log.info("Error" + errors.getAllErrors());
            return "registration";
        }

        if (captchaResponseDto.isSuccess()) {
            log.info("Fill captcha was successful");
            try {
                userService.create(userDto);
                model.addAttribute("message", "Letter with an activation code has been sent to your email");
                log.info(String.format("User %s created", userDto.getUsername()));
            } catch (UserAlreadyExistException e) {
                log.info("UserAlreadyExistException " + e.getDetail());
                model.addAttribute("errorMessage", e.getDetail());
                return "registration";
            }
        } else {
            model.addAttribute("errorMessage", "Please, fill captcha");
            log.info("Invalid fill captcha");
            return "registration";
        }

        return "/login";
    }

    @GetMapping("/activate/{code}")
    public String accountActivate(@PathVariable String code, Model model) {
        if (userService.isActivationCodePresent(code)) {
            log.info("Account has been successfully activated");
            model.addAttribute("successMessage", "Your account has been successfully activated");
        } else {
            log.info("Activation code is not found");
            model.addAttribute("unsuccessMessage", "Activation code is not found");
        }

        return "login";
    }



}
