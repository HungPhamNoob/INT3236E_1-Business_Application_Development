package com.example.demo.controller;

import com.example.demo.entity.UserProfile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@AuthenticationPrincipal OidcUser user, Model model) {
        model.addAttribute("isLoggedIn", user != null);
        return "index";
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal OidcUser user, Model model) {
        if (user != null) {
            UserProfile profile = new UserProfile();
            profile.setName(user.getFullName());
            profile.setEmail(user.getEmail());
            model.addAttribute("user", profile);
        }
        return "profile";
    }
}