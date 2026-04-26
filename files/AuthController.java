package com.chaisharma.chaimasala.controller;

import com.chaisharma.chaimasala.model.User;
import com.chaisharma.chaimasala.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    // ── Login page ────────────────────────────────────────────────────────────
    @GetMapping("/login")
    public String showLoginPage(Principal principal) {
        return (principal != null) ? "redirect:/" : "login";
    }

    // ── Register page ─────────────────────────────────────────────────────────
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // ── Register form submit ──────────────────────────────────────────────────
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        if (userService.existsByMobileNumber(user.getMobileNumber())) {
            model.addAttribute("error", "Mobile number already registered. Please login.");
            return "register";
        }
        userService.registerUser(user);
        return "redirect:/login?registered";
    }

    // ── Forgot password page ──────────────────────────────────────────────────
    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        return "forgot-password";
    }

    // ── Account page ──────────────────────────────────────────────────────────
    @GetMapping("/account")
    public String showAccountPage(Model model,
                                  Principal principal,
                                  @AuthenticationPrincipal OAuth2User oAuth2User) {
        if (oAuth2User != null) {
            model.addAttribute("name", oAuth2User.getAttribute("name"));
            model.addAttribute("email", oAuth2User.getAttribute("email"));
            model.addAttribute("loginType", "Google");
        } else if (principal != null) {
            model.addAttribute("name", principal.getName());
            model.addAttribute("loginType", "Standard");
        }
        return "account";
    }

    // ── Save Google user to DB on first OAuth2 login ──────────────────────────
    @GetMapping("/oauth2/post-login")
    public String handleOAuth2PostLogin(@AuthenticationPrincipal OAuth2User oAuth2User) {
        if (oAuth2User != null) {
            userService.findOrCreateOAuthUser(
                    oAuth2User.getAttribute("email"),
                    oAuth2User.getAttribute("name")
            );
        }
        return "redirect:/";
    }
}
