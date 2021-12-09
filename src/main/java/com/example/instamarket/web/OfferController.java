package com.example.instamarket.web;

import com.example.instamarket.model.binding.AddOfferBindingModel;
import com.example.instamarket.model.binding.EditOfferBindingModel;
import com.example.instamarket.model.enums.CategoriesEnum;
import com.example.instamarket.model.service.AddOfferServiceModel;
import com.example.instamarket.model.service.EditOfferServiceModel;
import com.example.instamarket.model.view.EditOfferViewModel;
import com.example.instamarket.model.view.OfferSellerViewModel;
import com.example.instamarket.service.OfferService;
import com.example.instamarket.service.SubscriberService;
import com.example.instamarket.service.UserService;
import com.example.instamarket.service.impl.InstamarketUser;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/offers")
public class OfferController {
    private final OfferService offerService;
    private final SubscriberService subscriberService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public OfferController(OfferService offerService, SubscriberService subscriberService, UserService userService, ModelMapper modelMapper) {
        this.offerService = offerService;
        this.subscriberService = subscriberService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public String showAddOfferPage(Model model){
        model.addAttribute("categories", CategoriesEnum.values());

       return "add-offer";
    }

    @PostMapping("/add")
    public String addOffer(@Valid AddOfferBindingModel addOfferBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes, @AuthenticationPrincipal InstamarketUser principal){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("addOfferBindingModel", addOfferBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addOfferBindingModel", bindingResult);

            return "redirect:/offers/add";
        }

        AddOfferServiceModel addOfferModel = modelMapper.map(addOfferBindingModel, AddOfferServiceModel.class);

        Long offerId = offerService.addOffer(addOfferModel, principal.getUserIdentifier());

        return "redirect:/offers/" + offerId + "/details";
    }

    @GetMapping("/{offerId}/edit")
    public String showEditOfferPage(@PathVariable Long offerId, Model model, @AuthenticationPrincipal InstamarketUser user){
        EditOfferViewModel offerDetails = offerService.getOfferDetails(offerId, user.getUserIdentifier());

        EditOfferBindingModel binding = modelMapper.map(offerDetails, EditOfferBindingModel.class);

        model.addAttribute("editOfferBindingModel", binding);
        model.addAttribute("offerDetails", offerDetails);
        model.addAttribute("categories", CategoriesEnum.values());

        return "edit-offer";
    }

    @GetMapping("/{offerId}/edit/errors")
    public String showEditErrors(@PathVariable Long offerId, Model model, @AuthenticationPrincipal InstamarketUser user){
        EditOfferViewModel offerDetails = offerService.getOfferDetails(offerId, user.getUserIdentifier());

        model.addAttribute("offerDetails", offerDetails);
        model.addAttribute("categories", CategoriesEnum.values());

        return "edit-offer";
    }

    @PreAuthorize("@offerServiceImpl.isOfferOwner(#user.username, #offerId)")
    @PatchMapping("/{offerId}/edit")
    public String editOffer(@PathVariable Long offerId, @Valid EditOfferBindingModel editOfferBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes, @AuthenticationPrincipal InstamarketUser user){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("editOfferBindingModel", editOfferBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.editOfferBindingModel", bindingResult);

            return "redirect:/offers/" + offerId + "/edit/errors";
        }

        EditOfferServiceModel serviceModel = modelMapper.map(editOfferBindingModel, EditOfferServiceModel.class);

        offerService.editOffer(serviceModel);

        return "redirect:/offers/" + offerId + "/details";
    }

    @ModelAttribute
    public AddOfferBindingModel addOfferBindingModel(){
        return new AddOfferBindingModel();
    }

    @ModelAttribute
    public EditOfferBindingModel editOfferBindingModel(){
        return new EditOfferBindingModel();
    }

    @PreAuthorize("@offerServiceImpl.isSpecOwner(#user.username, #id)")
    @GetMapping("/remove/specification/{id}")
    public ResponseEntity removeSpecification(@PathVariable Long id, @AuthenticationPrincipal InstamarketUser user){
        offerService.removeSpecification(id);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("@offerServiceImpl.isOptionOwner(#user.username, #id)")
    @GetMapping("/remove/option/{id}")
    public ResponseEntity removeOption(@PathVariable Long id, @AuthenticationPrincipal InstamarketUser user){
        offerService.removeOption(id);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("@offerServiceImpl.isOfferOwner(#user.username, #offerId)")
    @GetMapping("/{offerId}/remove")
    public String removeOffer(@PathVariable Long offerId, @AuthenticationPrincipal InstamarketUser user){
        offerService.removeOffer(offerId);

        return "redirect:/home";
    }

    @GetMapping("/{offerId}/details")
    public String showOfferDetails(@PathVariable Long offerId, Model model, @AuthenticationPrincipal InstamarketUser user){
        OfferSellerViewModel offerSeller = offerService.getOfferSeller(offerId);

        model.addAttribute("offer", offerService.getOffer(offerId, user.getUserIdentifier()));
        model.addAttribute("sellerInfo", offerSeller);
        model.addAttribute("isSubscriber", subscriberService.isSubscribed(offerSeller.getId(), user.getUserIdentifier()));
        model.addAttribute("feedback", offerService.getOfferFeedback(offerId));
        model.addAttribute("feedbackPercent", userService.getUserPositiveFeedback(offerSeller));

        return "offer";
    }
}
