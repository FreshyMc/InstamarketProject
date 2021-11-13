package com.example.instamarket.web;

import com.example.instamarket.model.binding.ProfileNamesBindingModel;
import com.example.instamarket.model.service.ProfileNamesServiceModel;
import com.example.instamarket.service.UserService;
import com.example.instamarket.service.impl.InstamarketUser;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public ProfileController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public String showProfilePage(){
        return "profile";
    }

    @PostMapping("/change/names")
    public String changeProfileNames(@Valid ProfileNamesBindingModel profileNamesBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes, @AuthenticationPrincipal InstamarketUser user){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("profileNamesBindingModel", profileNamesBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.profileNamesBindingModel", bindingResult);

            return "redirect:/profile";
        }

        ProfileNamesServiceModel profileNamesServiceModel = modelMapper.map(profileNamesBindingModel, ProfileNamesServiceModel.class);

        userService.changeNames(profileNamesServiceModel, user.getUserIdentifier());

        redirectAttributes.addFlashAttribute("profileNamesChanged", true);

        return "redirect:/profile";
    }

    @ModelAttribute
    public ProfileNamesBindingModel profileNamesBindingModel(){
        return new ProfileNamesBindingModel();
    }
}
