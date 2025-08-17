package com.duc.bookstore.controller;


import com.duc.bookstore.entity.User;
import com.duc.bookstore.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthenController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model){
        if (error != null){
            model.addAttribute("error", "username invalid or password invalid");
        }
        if (logout != null ){
            model.addAttribute("logout" ,"Logout successfully");
        }
        return "/authen/login";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user" , new User());
        return "/authen/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                               BindingResult bindingResult,
                               Model model){
        if(bindingResult.hasErrors()){
            return "/authen/register";
        }

        if(userService.existByUsername(user.getUsername())){
            model.addAttribute("error", "Username existed");
        }

        if (userService.existByEmail(user.getEmail())){
            model.addAttribute("error" , "Email existed");
        }

        userService.save(user);
        model.addAttribute("message" , "Register Successfully!");
        return "/authen/login";
    }
}
