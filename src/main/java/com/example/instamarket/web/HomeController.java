package com.example.instamarket.web;

import com.example.instamarket.misc.AuthHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final AuthHelper authHelper;

    public HomeController(AuthHelper authHelper) {
        this.authHelper = authHelper;
    }

    @GetMapping("/")
    public String showIndex(){
        if(authHelper.isLoggedIn()){
            return "redirect:/home";
        }

        return "index";
    }

    @GetMapping("/home")
    public String showHome(){
        return "home";
    }
}
