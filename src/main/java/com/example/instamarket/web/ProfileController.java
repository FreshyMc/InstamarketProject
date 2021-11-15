package com.example.instamarket.web;

import com.example.instamarket.model.binding.ChangePasswordBindingModel;
import com.example.instamarket.model.binding.ManageAddressesBindingModel;
import com.example.instamarket.model.binding.ProfileNamesBindingModel;
import com.example.instamarket.model.service.ChangePasswordServiceModel;
import com.example.instamarket.model.service.ProfileNamesServiceModel;
import com.example.instamarket.model.service.SaveAddressesServiceModel;
import com.example.instamarket.model.view.ProfileNamesViewModel;
import com.example.instamarket.service.AddressService;
import com.example.instamarket.service.UserService;
import com.example.instamarket.service.impl.InstamarketUser;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final UserService userService;
    private final AddressService addressService;
    private final ModelMapper modelMapper;
    private Logger logger = LoggerFactory.getLogger(ProfileController.class);

    public ProfileController(UserService userService, AddressService addressService, ModelMapper modelMapper) {
        this.userService = userService;
        this.addressService = addressService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public String showProfilePage(){
        return "profile";
    }

    @GetMapping("/change/names")
    public String showProfileChangeNamesForm(Model model, @AuthenticationPrincipal InstamarketUser user){
        if(!model.containsAttribute("profileNamesChanged")){
            model.addAttribute("profileNamesChanged", false);
        }

        model.addAttribute("profileNames", userService.takeUserNames(user.getUserIdentifier()));

        return "change-names";
    }

    @GetMapping("/change/names/error")
    public String changeNamesErrors(Model model){
        model.addAttribute("profileNames", new ProfileNamesViewModel());

        return "change-names";
    }

    @PostMapping("/change/names")
    public String changeProfileNames(@Valid ProfileNamesBindingModel profileNamesBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes, @AuthenticationPrincipal InstamarketUser user){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("profileNamesBindingModel", profileNamesBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.profileNamesBindingModel", bindingResult);

            return "redirect:/profile/change/names/error";
        }

        ProfileNamesServiceModel profileNamesServiceModel = modelMapper.map(profileNamesBindingModel, ProfileNamesServiceModel.class);

        userService.changeNames(profileNamesServiceModel, user.getUserIdentifier());

        redirectAttributes.addFlashAttribute("profileNamesChanged", true);

        return "redirect:/profile/change/names";
    }

    @GetMapping("/change/password")
    public String showProfileChangePasswordForm(Model model){
        if(!model.containsAttribute("passwordsDontMatch")){
            model.addAttribute("passwordsDontMatch", false);
        }

        if(!model.containsAttribute("incorrectPassword")){
            model.addAttribute("incorrectPassword", false);
        }

        return "change-password";
    }

    @PostMapping("/change/password")
    public String changePassword(@Valid ChangePasswordBindingModel changePasswordBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes, @AuthenticationPrincipal InstamarketUser user){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("changePasswordBindingModel", changePasswordBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.changePasswordBindingModel", bindingResult);

            return "redirect:/profile/change/password";
        }

        if(!changePasswordBindingModel.passwordMatch()){
            redirectAttributes.addFlashAttribute("changePasswordBindingModel", changePasswordBindingModel);
            redirectAttributes.addFlashAttribute("passwordsDontMatch", true);

            return "redirect:/profile/change/password";
        }

        ChangePasswordServiceModel model = modelMapper.map(changePasswordBindingModel, ChangePasswordServiceModel.class);

        boolean changePasswordSuccess = userService.changeUserPassword(model, user.getUserIdentifier());

        if(!changePasswordSuccess){
            redirectAttributes.addFlashAttribute("changePasswordBindingModel", changePasswordBindingModel);
            redirectAttributes.addFlashAttribute("incorrectPassword", true);

            return "redirect:/profile/change/password";
        }

        return "redirect:/profile/change/password";
    }

    @GetMapping("/change/picture")
    public String showProfileChangePictureForm(){
        return "change-picture";
    }

    @GetMapping("/manage/addresses")
    public String showManageAddressesForm(Model model, @AuthenticationPrincipal InstamarketUser user){
        model.addAttribute("addresses", userService.takeUserAddresses(user.getUserIdentifier()));

        return "manage-addresses";
    }

    @DeleteMapping("/manage/addresses/{id}/delete")
    public String deleteAddress(@PathVariable Long id){
        addressService.deleteAddress(id);

        return "redirect:/profile/manage/addresses";
    }

    @PostMapping("/manage/addresses")
    public String manageAddresses(@Valid ManageAddressesBindingModel manageAddressesBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes, @AuthenticationPrincipal InstamarketUser user){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("manageAddressesBindingModel", manageAddressesBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.manageAddressesBindingModel", bindingResult);

            return "redirect:/profile/manage/addresses";
        }

        SaveAddressesServiceModel model = modelMapper.map(manageAddressesBindingModel, SaveAddressesServiceModel.class);

        userService.saveNewAddresses(model, user.getUserIdentifier());

        return "redirect:/profile/manage/addresses";
    }

    @ModelAttribute
    public ManageAddressesBindingModel manageAddressesBindingModel(){
        return new ManageAddressesBindingModel();
    }

    @ModelAttribute
    public ProfileNamesBindingModel profileNamesBindingModel(){
        return new ProfileNamesBindingModel();
    }

    @ModelAttribute
    public ChangePasswordBindingModel changePasswordBindingModel(){
        return new ChangePasswordBindingModel();
    }
}
