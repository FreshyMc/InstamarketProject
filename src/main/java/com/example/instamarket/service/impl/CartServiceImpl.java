package com.example.instamarket.service.impl;

import com.example.instamarket.exception.OfferNotFoundException;
import com.example.instamarket.exception.UserNotFoundException;
import com.example.instamarket.model.binding.AddToCartBindingModel;
import com.example.instamarket.model.dto.CartDTO;
import com.example.instamarket.model.entity.*;
import com.example.instamarket.model.enums.OrderStatusEnum;
import com.example.instamarket.model.service.CheckoutServiceModel;
import com.example.instamarket.model.view.CartItemViewModel;
import com.example.instamarket.repository.*;
import com.example.instamarket.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final OfferRepository offerRepository;
    private final OfferOptionRepository offerOptionRepository;
    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public CartServiceImpl(CartRepository cartRepository, OfferRepository offerRepository, OfferOptionRepository offerOptionRepository, OrderRepository orderRepository, OrderStatusRepository orderStatusRepository, AddressRepository addressRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.cartRepository = cartRepository;
        this.offerRepository = offerRepository;
        this.offerOptionRepository = offerOptionRepository;
        this.orderRepository = orderRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public CartDTO addToCart(Long offerId, AddToCartBindingModel optionModel, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException());

        Offer offer = offerRepository.findById(offerId).orElseThrow(()-> new OfferNotFoundException());

        OfferOption offerOption = null;

        if(optionModel.getOfferOptionIndex() != null){
            offerOption = offer.getOfferOptions().get(optionModel.getOfferOptionIndex());
        }

        Cart cartItem = cartRepository.findCartByBuyerAndOfferAndOfferOption(user, offer, offerOption).orElse(null);

        if(cartItem != null){
            CartDTO item = new CartDTO();

            if(cartItem.isRemoved()){
                cartItem.setRemoved(false);

                cartRepository.save(cartItem);
            }

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
    public CartDTO removeFromCart(Long cartItemId) {
        Cart cartItem = cartRepository.findById(cartItemId).orElse(null);

        if(cartItem == null){
            CartDTO item = new CartDTO();

            item.setAdded(false).setOfferId(cartItemId);

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
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException());

        return cartRepository.findAllByBuyerAndRemovedOrderByAddedAtDesc(user, false).stream().map(this::toCartItem).collect(Collectors.toList());
    }

    @Override
    public void checkoutCart(CheckoutServiceModel model, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException());

        //TODO Custom error object not found exception
        Address userAddress = addressRepository.findById(model.getDeliveryAddress()).orElseThrow();

        model.getCartItems().stream().map(offer -> {
            Cart cartItem = cartRepository.findById(offer.getOfferId()).orElseThrow();

            Offer mappedOffer = cartItem.getOffer();

            Order order = new Order();

            OrderStatus initialStatus = orderStatusRepository.findOrderStatusByName(OrderStatusEnum.WAITING).get();

            BigDecimal quantity = new BigDecimal(offer.getQuantity());

            BigDecimal orderTotalPrice = mappedOffer.getPrice().multiply(quantity);

            order.
                    setBuyer(user).
                    setOffer(mappedOffer).
                    setQuantity(offer.getQuantity()).
                    setDeliveryAddress(userAddress).
                    setTotalPrice(orderTotalPrice).
                    setOfferOption(cartItem.getOfferOption()).
                    setStatus(initialStatus);

            cartRepository.delete(cartItem);

            return order;
        }).forEach(orderRepository::save);
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
        item.setCartId(cartItem.getId());

        return item;
    }
}
