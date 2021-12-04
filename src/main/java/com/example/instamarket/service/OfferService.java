package com.example.instamarket.service;

import com.example.instamarket.model.dto.OfferDTO;
import com.example.instamarket.model.service.AddOfferServiceModel;
import com.example.instamarket.model.service.OfferQuestionServiceModel;
import com.example.instamarket.model.service.SearchServiceModel;
import com.example.instamarket.model.view.OfferDetailsViewModel;
import com.example.instamarket.model.view.OfferSellerViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

public interface OfferService {
    void initializeOfferCategories();

    Long addOffer(AddOfferServiceModel model, String username);

    OfferDetailsViewModel getOffer(Long offerId, String username);

    Slice<OfferDTO> getSellerOffers(int pageNo, int pageSize, String sortBy, Long id);

    Page<OfferDTO> getOffers(int pageNo, int pageSize, String sortBy);

    Page<OfferDTO> searchOffers(int pageNo, int pageSize, String sortBy, SearchServiceModel model, String username);

    OfferSellerViewModel getOfferSeller(Long offerId);

    void saveOfferQuestion(OfferQuestionServiceModel model, String username);
}
