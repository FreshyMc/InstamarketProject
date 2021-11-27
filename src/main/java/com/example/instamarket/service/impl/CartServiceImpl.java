package com.example.instamarket.service.impl;

import com.example.instamarket.model.binding.AddToCartBindingModel;
import com.example.instamarket.model.dto.CartDTO;
import com.example.instamarket.model.entity.Cart;
import com.example.instamarket.model.entity.Offer;
import com.example.instamarket.model.entity.OfferOption;
import com.example.instamarket.model.entity.User;
import com.example.instamarket.model.view.CartItemViewModel;
import com.example.instamarket.repository.CartRepository;
import com.example.instamarket.repository.OfferOptionRepository;
import com.example.instamarket.repository.OfferRepository;
import com.example.instamarket.repository.UserRepository;
import com.example.instamarket.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final OfferRepository offerRepository;
    private final OfferOptionRepository offerOptionRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public CartServiceImpl(CartRepository cartRepository, OfferRepository offerRepository, OfferOptionRepository offerOptionRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.cartRepository = cartRepository;
        this.offerRepository = offerRepository;
        this.offerOptionRepository = offerOptionRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
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

    @Override
    public List<CartItemViewModel> getAllItems(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();

        return cartRepository.findAllByBuyerAndRemovedOrderByAddedAtDesc(user, false).stream().map(this::toCartItem).collect(Collectors.toList());
    }

    private CartItemViewModel toCartItem(Cart cartItem){
        Offer offer = cartItem.getOffer();

        CartItemViewModel item = modelMapper.map(offer, CartItemViewModel.class);

        Set<String> offerImagesUrl = new HashSet<>();

        offer.getImages().forEach(img -> {
            offerImagesUrl.add(img.getImageUrl());
        });

        if(cartItem.getOfferOption() != null){
            item.setOptionKey(cartItem.getOfferOption().getOptionName());
            item.setOptionValue(cartItem.getOfferOption().getOptionValue());
        }

        item.setOfferImages(offerImagesUrl);
        item.setSellerUsername(offer.getSeller().getUsername());
        item.setSellerProfilePicture(offer.getSeller().getProfilePicture().getUrl());

        return item;
    }
}
