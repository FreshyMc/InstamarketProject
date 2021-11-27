package com.example.instamarket.web;

import com.example.instamarket.misc.AuthHelper;
import com.example.instamarket.model.binding.UserRegistrationBindingModel;
import com.example.instamarket.model.service.UserRegisterServiceModel;
import com.example.instamarket.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class AuthController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final AuthHelper authHelper;

    public AuthController(UserService userService, ModelMapper modelMapper, AuthHelper authHelper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.authHelper = authHelper;
    }

    @GetMapping("/login")
    public String showLogin(Model model){
        if(authHelper.isLoggedIn()){
            return "redirect:/home";
        }

        if(!model.containsAttribute("passwordChanged")){
            model.addAttribute("passwordChanged", false);
        }

        return "login";
    }

    @PostMapping("/login-error")
    public String failedLogin(@ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("loginFailed", true);
        redirectAttributes.addFlashAttribute("username", username);

        return "redirect:/login";
    }

    @GetMapping("/register")
    public String showRegister(Model model){
        if(authHelper.isLoggedIn()){
            return "redirect:/home";
        }

        if(!model.containsAttribute("passwordsDontMatch")){
            model.addAttribute("passwordsDontMatch", false);
        }

        if(!model.containsAttribute("usernameTaken")){
            model.addAttribute("usernameTaken", false);
        }

        if(!model.containsAttribute("emailAlreadyRegistered")){
            model.addAttribute("emailAlreadyRegistered", false);
        }

        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid UserRegistrationBindingModel userRegistrationBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("userRegistrationBindingModel", userRegistrationBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationBindingModel", bindingResult);

            return "redirect:/register";
        }

        if(!userRegistrationBindingModel.passwordsMatch()){
            redirectAttributes.addFlashAttribute("userRegistrationBindingModel", userRegistrationBindingModel);
            redirectAttributes.addFlashAttribute("passwordsDontMatch", true);

            return "redirect:/register";
        }

        if(!userService.isUsernameTaken(userRegistrationBindingModel.getUsername())){
            redirectAttributes.addFlashAttribute("userRegistrationBindingModel", userRegistrationBindingModel);
            redirectAttributes.addFlashAttribute("usernameTaken", true);

            return "redirect:/register";
        }

        if(!userService.isEmailAlreadyRegistered(userRegistrationBindingModel.getEmail())){
            redirectAttributes.addFlashAttribute("userRegistrationBindingModel", userRegistrationBindingModel);
            redirectAttributes.addFlashAttribute("emailAlreadyRegistered", true);

            return "redirect:/register";
        }

        UserRegisterServiceModel user = modelMapper.map(userRegistrationBindingModel, UserRegisterServiceModel.class);

        userService.registerUser(user);

        return "redirect:/home";
    }

    @ModelAttribute
    public UserRegistrationBindingModel userRegistrationBindingModel(){
        return new UserRegistrationBindingModel();
    }
}
