package com.example.instamarket.web;

import com.example.instamarket.misc.AuthHelper;
import com.example.instamarket.model.binding.ChangePasswordBindingModel;
import com.example.instamarket.model.binding.ManageAddressesBindingModel;
import com.example.instamarket.model.binding.ProfileNamesBindingModel;
import com.example.instamarket.model.binding.ProfilePictureBindingModel;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final UserService userService;
    private final AddressService addressService;
    private final ModelMapper modelMapper;
    private final AuthHelper authHelper;
    private Logger logger = LoggerFactory.getLogger(ProfileController.class);

    public ProfileController(UserService userService, AddressService addressService, ModelMapper modelMapper, AuthHelper authHelper) {
        this.userService = userService;
        this.addressService = addressService;
        this.modelMapper = modelMapper;
        this.authHelper = authHelper;
    }

    @GetMapping
    public String showProfilePage(Model model, @AuthenticationPrincipal InstamarketUser user) {
        model.addAttribute("profilePicture", userService.getProfilePicture(user.getUserIdentifier()));
        model.addAttribute("profileNames", userService.takeUserNames(user.getUserIdentifier()));
        model.addAttribute("sellerApplied", userService.hasAppliedToBecomeSeller(user.getUserIdentifier()));

        return "profile";
    }

    @GetMapping("/change/names")
    public String showProfileChangeNamesForm(Model model, @AuthenticationPrincipal InstamarketUser user){
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
        return "change-password";
    }

    @PostMapping("/change/password")
    public String changePassword(@Valid ChangePasswordBindingModel changePasswordBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes, @AuthenticationPrincipal InstamarketUser user, HttpServletRequest request, HttpServletResponse response){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("changePasswordBindingModel", changePasswordBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.changePasswordBindingModel", bindingResult);

            return "redirect:/profile/change/password";
        }

        if(!changePasswordBindingModel.passwordMatch()){
            //redirectAttributes.addFlashAttribute("changePasswordBindingModel", changePasswordBindingModel);
            redirectAttributes.addFlashAttribute("passwordsDontMatch", true);

            return "redirect:/profile/change/password";
        }

        ChangePasswordServiceModel model = modelMapper.map(changePasswordBindingModel, ChangePasswordServiceModel.class);

        boolean changePasswordSuccess = userService.changeUserPassword(model, user.getUserIdentifier());

        if(!changePasswordSuccess){
            //redirectAttributes.addFlashAttribute("changePasswordBindingModel", changePasswordBindingModel);
            redirectAttributes.addFlashAttribute("incorrectPassword", true);

            return "redirect:/profile/change/password";
        }

        authHelper.logout(request, response);

        redirectAttributes.addFlashAttribute("passwordChanged", true);

        return "redirect:/login";
    }

    //TODO Make profile picture upload with cloudinary
    @GetMapping("/change/picture")
    public String showProfileChangePictureForm(){
        return "change-picture";
    }

    @PostMapping("/change/picture")
    public String changeProfilePicture(ProfilePictureBindingModel bindingModel, RedirectAttributes redirectAttributes, @AuthenticationPrincipal InstamarketUser user) throws IOException {
        boolean result = userService.changeProfilePicture(bindingModel, user.getUserIdentifier());

        logger.info("Upload result: {}", result);

        if(result){
            redirectAttributes.addFlashAttribute("pictureAlert", "Successfully changed your profile picture.");
        }else{
            redirectAttributes.addFlashAttribute("pictureAlert", "We failed to change your profile picture. Try again later!");
        }

        return "redirect:/profile";
    }

    @GetMapping("/manage/addresses")
    public String showManageAddressesForm(Model model, @AuthenticationPrincipal InstamarketUser user){
        model.addAttribute("addresses", userService.takeUserAddresses(user.getUserIdentifier()));

        return "manage-addresses";
    }

    @PreAuthorize("isAddressOwner(#id)")
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

    @GetMapping("/become-seller")
    public String becomeSeller(@AuthenticationPrincipal InstamarketUser user, RedirectAttributes redirectAttributes){
        userService.becomeSeller(user.getUserIdentifier());

        redirectAttributes.addFlashAttribute("sellerRequest", "You successfully applied to become a seller.");

        return "redirect:/profile";
    }

    @GetMapping("{id}/showcase")
    public String showcaseProfile(@PathVariable Long id){
        //TODO Profile Showcase page
        return "redirect:/home";
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

    @ModelAttribute
    public ProfilePictureBindingModel profilePictureBindingModel(){
        return new ProfilePictureBindingModel();
    }
}
