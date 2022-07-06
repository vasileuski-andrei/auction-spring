package com.starlight.controller;

import com.starlight.dto.UserDto;
import com.starlight.exception.ValidationException;
import com.starlight.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/user-profile")
public class UserProfileController {

    private final UserService userService;
    private String username;

    @Autowired
    public UserProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getUserProfilePage(@ModelAttribute("userDto") UserDto userDto, Principal principal) {
        this.username = principal.getName();
        log.info(String.format("Open user page of %s", username));
        return "user-profile";
    }

    @PostMapping("/delete-account")
    public String deleteUserByUsername(@RequestParam("password") String password, HttpServletRequest request, Model model) {
        try {
            userService.deleteByUsername(username, password);
            log.info(String.format("Delete account of %s", username));
            request.logout();
            log.info(String.format("Logout user %s", username));
        } catch (ValidationException e) {
            log.info("ValidationException " + e.getDetail());
            model.addAttribute("errorMessage", e.getDetail());
            return "user-profile";
        } catch (DataIntegrityViolationException e) {
            String errorMessage = "Your account hasn't been deleted. Probably you have some lot in the market";
            log.info("ValidationException " + errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            return "user-profile";
        } catch (ServletException e) {
            log.info("ServletException");
            e.printStackTrace();
        }

        return "redirect:/index";
    }

    @PostMapping("/change-password")
    public String changeUserPassword(@ModelAttribute("userDto") UserDto userDto,
                                     @RequestParam("oldPassword") String oldPassword, Model model) {

        userDto.setUsername(username);

        try {
            userService.changePassword(oldPassword, userDto);
            log.info(String.format("User %s has successfully changed his password", userDto.getUsername()));
            model.addAttribute("message", "You have successfully changed your password");
        } catch (ValidationException e) {
            log.info("ValidationException " + e.getDetail());
            model.addAttribute("errorMessage", e.getDetail());
            return "user-profile";
        }

        return "user-profile";

    }
}
