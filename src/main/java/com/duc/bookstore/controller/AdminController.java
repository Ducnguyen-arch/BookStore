package com.duc.bookstore.controller;


import com.duc.bookstore.entity.Book;
import com.duc.bookstore.entity.User;
import com.duc.bookstore.service.BookService;
import com.duc.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @GetMapping("/dashboard")
    public String dashboard(Model model){
        List<User> listUsers = userService.findAllUsers();
        long totalBooks = bookService.findAll().size();

        model.addAttribute("totalUsers", listUsers.size());
        model.addAttribute("totalBooks", totalBooks);
        model.addAttribute("users" , listUsers);

        return "admin/dashboard";
    }

    @GetMapping("/users")
    public String usersList(Model model){
        model.addAttribute("users" , userService.findAllUsers());
        return "/admin/users";

//        List<User> users = userService.findAllUsers();
//        // Make sure no null users are in the list
//        users = users.stream()
//                .filter(Objects::nonNull)
//                .collect(Collectors.toList());
//        model.addAttribute("users", users);
//        return "admin/users";
    }

    @GetMapping("/users/{id}")
    public String  displayUsers(@PathVariable Long id ,  Model model){
        Optional<User> user = userService.findById(id);
        if (user.isPresent()){
            model.addAttribute("user" , user.get());
            model.addAttribute("bookCount" , bookService.countBookByUser(id));
            return "admin/user-display-detail";
        }
        return "redirect:/admin/users";
    }


    @PostMapping("/users/{id}/status")
    public String userStatus(@PathVariable Long id){
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()){
            User user = userOptional.get();
            user.setEnabled(!user.isEnabled());
            userService.updateUser(user);
        }
        return "redirect:/admin/users";
    }

}
