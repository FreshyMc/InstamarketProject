package com.example.instamarket.web;

import com.example.instamarket.model.binding.OfferQuestionBindingModel;
import com.example.instamarket.model.binding.SearchOfferBindingModel;
import com.example.instamarket.model.dto.FavouriteOfferDTO;
import com.example.instamarket.model.dto.OfferDTO;
import com.example.instamarket.model.service.OfferQuestionServiceModel;
import com.example.instamarket.model.service.SearchServiceModel;
import com.example.instamarket.service.OfferService;
import com.example.instamarket.service.UserService;
import com.example.instamarket.service.impl.InstamarketUser;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/offers")
public class OfferRestController {
    private final OfferService offerService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public OfferRestController(OfferService offerService, UserService userService, ModelMapper modelMapper) {
        this.offerService = offerService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<Page<OfferDTO>> getOffers(
            @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "6") Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy){

        return ResponseEntity.ok(offerService.getOffers(pageNo, pageSize, sortBy));
    }

    @PostMapping("/search")
    public ResponseEntity<Page<OfferDTO>> searchOffers(
            @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "6") Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy,
            @RequestBody @Valid SearchOfferBindingModel searchModel,
            @AuthenticationPrincipal InstamarketUser user){

        SearchServiceModel model = modelMapper.map(searchModel, SearchServiceModel.class);

        return ResponseEntity.ok(offerService.searchOffers(pageNo, pageSize, sortBy, model, user.getUserIdentifier()));
    }

    @PostMapping("/{offerId}/favourite")
    public ResponseEntity<FavouriteOfferDTO> addOfferToFavourite(@PathVariable Long offerId, @AuthenticationPrincipal InstamarketUser user){
        FavouriteOfferDTO model = userService.addToWishList(offerId, user.getUserIdentifier());

        return ResponseEntity.ok(model);
    }

    @PostMapping("/question")
    public ResponseEntity addOfferQuestion(@RequestBody @Valid OfferQuestionBindingModel questionBindingModel, @AuthenticationPrincipal InstamarketUser user){
        OfferQuestionServiceModel model = modelMapper.map(questionBindingModel, OfferQuestionServiceModel.class);

        offerService.saveOfferQuestion(model, user.getUserIdentifier());

        return ResponseEntity.ok().build();
    }
}
