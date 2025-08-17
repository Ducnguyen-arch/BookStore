package com.duc.bookstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(){
        return "redirect:/books";
    }

    @GetMapping("/home")
    public String homePage(){
        return "redirect:/books";
    }

    @GetMapping("/access-denied")
    public String accessDenied(){
        return "redirect:/access-denied";
    }
}
