package com.example.instamarket.web;

import com.example.instamarket.model.binding.AddToCartBindingModel;
import com.example.instamarket.model.binding.CheckoutBindingModel;
import com.example.instamarket.model.dto.CartDTO;
import com.example.instamarket.model.entity.Cart;
import com.example.instamarket.model.service.CheckoutServiceModel;
import com.example.instamarket.service.CartService;
import com.example.instamarket.service.impl.InstamarketUser;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/cart")
public class CartRestController {
    private final CartService cartService;
    private final ModelMapper modelMapper;

    public CartRestController(CartService cartService, ModelMapper modelMapper) {
        this.cartService = cartService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/{offerId}/add")
    public ResponseEntity<CartDTO> addOfferToCart(@PathVariable Long offerId, @RequestBody AddToCartBindingModel option, @AuthenticationPrincipal InstamarketUser user){
        CartDTO model = cartService.addToCart(offerId, option, user.getUserIdentifier());

        return ResponseEntity.ok(model);
    }

    @PostMapping("/{cartItemId}/remove")
    public ResponseEntity<CartDTO> removeOfferFromCart(@PathVariable Long cartItemId){
        CartDTO model = cartService.removeFromCart(cartItemId);

        return ResponseEntity.ok(model);
    }

    @PostMapping("/checkout")
    public ResponseEntity checkoutCart(@RequestBody @Valid CheckoutBindingModel bindingModel, @AuthenticationPrincipal InstamarketUser user){
        CheckoutServiceModel model = modelMapper.map(bindingModel, CheckoutServiceModel.class);

        cartService.checkoutCart(model, user.getUserIdentifier());

        return ResponseEntity.ok().build();
    }
}
