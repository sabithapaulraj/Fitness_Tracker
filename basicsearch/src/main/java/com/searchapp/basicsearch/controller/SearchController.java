package com.searchapp.basicsearch.controller;

import com.searchapp.basicsearch.dto.SearchRequest;
import com.searchapp.basicsearch.dto.SearchResponse;
import com.searchapp.basicsearch.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // ✅ Correct Model import
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SearchController {

    private final UserService service;

    public SearchController(UserService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("request", new SearchRequest()); // Binds empty form data
        return "search"; // Thymeleaf will render search.html
    }

    @PostMapping("/search")
    public String doSearch(@ModelAttribute("request") SearchRequest request, Model model) {
        SearchResponse response = service.searchUsers(request);
        model.addAttribute("response", response);
        return "result"; // Thymeleaf will render result.html
    }
}
