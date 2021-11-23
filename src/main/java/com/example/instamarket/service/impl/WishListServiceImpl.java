package com.example.instamarket.service.impl;

import com.example.instamarket.model.entity.Offer;
import com.example.instamarket.model.entity.User;
import com.example.instamarket.model.entity.WishList;
import com.example.instamarket.model.view.WishListItemViewModel;
import com.example.instamarket.repository.UserRepository;
import com.example.instamarket.repository.WishListRepository;
import com.example.instamarket.service.WishListService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class WishListServiceImpl implements WishListService {
    private final WishListRepository wishListRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public WishListServiceImpl(WishListRepository wishListRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.wishListRepository = wishListRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<WishListItemViewModel> getWishListItems(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();

        List<WishListItemViewModel> wishListItems = wishListRepository.findAllUserWishListItems(user.getId()).stream().map(item -> {
            Offer offer = item.getOffer();

            WishListItemViewModel wishListItem = modelMapper.map(offer, WishListItemViewModel.class);

            Set<String> offerImages = offer.getImages().stream().map(img -> {
                return img.getImageUrl();
            }).collect(Collectors.toSet());

            wishListItem.setImages(offerImages);

            wishListItem.setAddedAt(item.getAddedAt());

            return wishListItem;
        }).collect(Collectors.toList());

        return wishListItems;
    }
}
