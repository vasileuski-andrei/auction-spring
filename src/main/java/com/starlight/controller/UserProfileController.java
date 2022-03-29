package com.starlight.controller;

import com.starlight.exception.ValidationException;
import com.starlight.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping("/user-profile")
public class UserProfileController {

    private final UserService userService;

    @Autowired
    public UserProfileController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public String getUserProfilePage() {
        return "user-profile";
    }

    @PostMapping("/delete-account")
    public String deleteUserByUsername(@RequestParam("password") String password,
                                       HttpServletRequest request, Model model, Principal principal) {

        try {
            userService.deleteByUsername(principal.getName(), password);
            request.logout();
        } catch (ValidationException e) {
            model.addAttribute("errorMessage", e.getDetail());
            return "user-profile";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("errorMessage", "Your account hasn't been deleted. Probably you have some lot in the market");
            return "user-profile";
        } catch (ServletException e) {
            e.printStackTrace();
        }

        return "redirect:/index";
    }
}
