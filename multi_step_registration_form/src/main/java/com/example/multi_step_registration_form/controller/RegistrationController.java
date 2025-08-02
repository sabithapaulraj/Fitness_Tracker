package com.example.multi_step_registration_form.controller;

import com.example.multi_step_registration_form.model.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @GetMapping("/step1")
    public String step1Form(Model model) {
        model.addAttribute("user", new User());
        return "step1";
    }

    @PostMapping("/step1")
    public String handleStep1(@ModelAttribute("user") @Valid User user,
                              BindingResult result,
                              HttpSession session) {
        if (result.hasFieldErrors("name") || result.hasFieldErrors("email")) {
            return "step1";
        }
        session.setAttribute("user", user);
        return "redirect:/register/step2";
    }

    @GetMapping("/step2")
    public String step2Form(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/register/step1";
        model.addAttribute("user", user);
        return "step2";
    }

    @PostMapping("/step2")
    public String handleStep2(@ModelAttribute("user") @Valid User formUser,
                              BindingResult result,
                              HttpSession session) {
        if (result.hasFieldErrors("username") ||
                result.hasFieldErrors("password") ||
                result.hasFieldErrors("confirmPassword")) {
            return "step2";
        }

        if (!formUser.getPassword().equals(formUser.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error.user", "Passwords do not match");
            return "step2";
        }

        User user = (User) session.getAttribute("user");
        user.setUsername(formUser.getUsername());
        user.setPassword(formUser.getPassword());
        user.setConfirmPassword(formUser.getConfirmPassword());
        session.setAttribute("user", user);

        return "redirect:/register/confirm";
    }

    @GetMapping("/confirm")
    public String confirm(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/register/step1";
        model.addAttribute("user", user);
        return "confirm";
    }

    @PostMapping("/submit")
    public String submit(HttpSession session) {
        session.invalidate(); 
        return "success";
    }
}
