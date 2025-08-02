package com.example.springmvcform.controller;
import com.example.springmvcform.model.Address;
import com.example.springmvcform.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@Controller
public class FormController {
    @GetMapping("/")
    public String showForm(Model model) {
        User user = new User();
        ArrayList<Address> addresses = new ArrayList<>();
        addresses.add(new Address());
        user.setAddresses(addresses);
        ArrayList<String> phones = new ArrayList<>();
        phones.add("");
        user.setPhones(phones);
        model.addAttribute("user", user);
        return "form";
    }

    @PostMapping("/submit")
    public String submitForm(@ModelAttribute User user, Model model) {
        model.addAttribute("submittedUser", user);
        return "result";
    }
}
