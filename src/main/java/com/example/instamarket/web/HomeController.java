package com.example.instamarket.web;

import com.example.instamarket.misc.AuthHelper;
import com.example.instamarket.model.enums.CategoriesEnum;
import com.example.instamarket.model.enums.SearchCategoriesEnum;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String showHome(Model model){
        model.addAttribute("categories", SearchCategoriesEnum.values());

        return "home";
    }
}
