package com.example.instamarket.service.impl;

import com.example.instamarket.model.binding.AddToCartBindingModel;
import com.example.instamarket.model.dto.CartDTO;
import com.example.instamarket.model.entity.Cart;
import com.example.instamarket.model.entity.Offer;
import com.example.instamarket.model.entity.OfferOption;
import com.example.instamarket.model.entity.User;
import com.example.instamarket.repository.CartRepository;
import com.example.instamarket.repository.OfferOptionRepository;
import com.example.instamarket.repository.OfferRepository;
import com.example.instamarket.repository.UserRepository;
import com.example.instamarket.service.CartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final OfferRepository offerRepository;
    private final OfferOptionRepository offerOptionRepository;
    private final UserRepository userRepository;

    public CartServiceImpl(CartRepository cartRepository, OfferRepository offerRepository, OfferOptionRepository offerOptionRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.offerRepository = offerRepository;
        this.offerOptionRepository = offerOptionRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public CartDTO addToCart(Long offerId, AddToCartBindingModel optionModel, String username) {
        User user = userRepository.findByUsername(username).orElseThrow();

        Offer offer = offerRepository.findById(offerId).orElseThrow();

        OfferOption offerOption = null;

        if(optionModel.getOfferOptionIndex() != null){
            offerOption = offer.getOfferOptions().get(optionModel.getOfferOptionIndex());
        }

        Cart cartItem = cartRepository.findCartByBuyerAndOfferAndOfferOption(user, offer, offerOption).orElse(null);

        if(cartItem != null){
            CartDTO item = new CartDTO();

            item.setAdded(true).setOfferId(offerId);

            return item;
        }

        Cart addedItem = new Cart();

        addedItem.setBuyer(user).setOffer(offer).setOfferOption(offerOption);

        Cart saved = cartRepository.save(addedItem);

        CartDTO item = new CartDTO();

        item.setAdded(true).setOfferId(saved.getId());

        return item;
    }

    @Override
    public CartDTO removeFromCart(Long offerId, AddToCartBindingModel optionModel, String username) {
        User user = userRepository.findByUsername(username).orElseThrow();

        Offer offer = offerRepository.findById(offerId).orElseThrow();

        OfferOption offerOption = null;

        if(optionModel.getOfferOptionIndex() != null){
            offerOption = offer.getOfferOptions().get(optionModel.getOfferOptionIndex());
        }

        Cart cartItem = cartRepository.findCartByBuyerAndOfferAndOfferOption(user, offer, offerOption).orElse(null);

        if(cartItem == null){
            CartDTO item = new CartDTO();

            item.setAdded(false).setOfferId(offerId);

            return item;
        }

        cartItem.setRemoved(true);

        Cart saved = cartRepository.save(cartItem);

        CartDTO item = new CartDTO();

        item.setAdded(saved.isRemoved()).setOfferId(saved.getId());

        return item;
    }
}
