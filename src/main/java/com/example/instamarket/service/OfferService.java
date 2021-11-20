package com.example.instamarket.service;

import com.example.instamarket.model.dto.OfferDTO;
import com.example.instamarket.model.service.AddOfferServiceModel;
import com.example.instamarket.model.service.SearchServiceModel;
import com.example.instamarket.model.view.OfferDetailsViewModel;
import org.springframework.data.domain.Page;

public interface OfferService {
    void initializeShippingTypes();

    void initializeOfferCategories();

    Long addOffer(AddOfferServiceModel model, String username);

    OfferDetailsViewModel getOffer(Long offerId, String username);

    Page<OfferDTO> getOffers(int pageNo, int pageSize, String sortBy);

    Page<OfferDTO> searchOffers(int pageNo, int pageSize, String sortBy, SearchServiceModel model);
}
