package com.example.instamarket.web;

import com.example.instamarket.model.binding.AddOfferBindingModel;
import com.example.instamarket.model.enums.CategoriesEnum;
import com.example.instamarket.model.enums.ShippingTypesEnum;
import com.example.instamarket.model.service.AddOfferServiceModel;
import com.example.instamarket.service.OfferService;
import com.example.instamarket.service.impl.InstamarketUser;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/offers")
public class OfferController {
    private final OfferService offerService;
    private final ModelMapper modelMapper;

    public OfferController(OfferService offerService, ModelMapper modelMapper) {
        this.offerService = offerService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public String showAddOfferPage(Model model){
        model.addAttribute("categories", CategoriesEnum.values());
        model.addAttribute("shipping", ShippingTypesEnum.values());

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

    @ModelAttribute
    public AddOfferBindingModel addOfferBindingModel(){
        return new AddOfferBindingModel();
    }

    @GetMapping("/demo")
    public String showDemoOffer(){
        return "offer";
    }

    @GetMapping("/{offerId}/details")
    public String showOfferDetails(@PathVariable Long offerId, Model model, Principal principal){
        model.addAttribute("offer", offerService.getOffer(offerId, principal.getName()));

        return "offer";
    }
}
