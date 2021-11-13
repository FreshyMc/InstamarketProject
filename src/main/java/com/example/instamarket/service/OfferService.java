package com.example.instamarket.service;

import com.example.instamarket.model.service.AddOfferServiceModel;
import com.example.instamarket.model.view.OfferDetailsViewModel;

public interface OfferService {
    void initializeShippingTypes();

    void initializeOfferCategories();

    Long addOffer(AddOfferServiceModel model, String username);

    OfferDetailsViewModel getOffer(Long offerId, String username);
}
