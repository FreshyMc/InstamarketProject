package com.example.instamarket.service;

import com.example.instamarket.model.binding.EditOfferBindingModel;
import com.example.instamarket.model.dto.OfferDTO;
import com.example.instamarket.model.service.AddOfferServiceModel;
import com.example.instamarket.model.service.EditOfferServiceModel;
import com.example.instamarket.model.service.OfferQuestionServiceModel;
import com.example.instamarket.model.service.SearchServiceModel;
import com.example.instamarket.model.view.EditOfferViewModel;
import com.example.instamarket.model.view.FeedbackViewModel;
import com.example.instamarket.model.view.OfferDetailsViewModel;
import com.example.instamarket.model.view.OfferSellerViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface OfferService {
    void initializeOfferCategories();

    Long addOffer(AddOfferServiceModel model, String username);

    OfferDetailsViewModel getOffer(Long offerId, String username);

    Slice<OfferDTO> getSellerOffers(int pageNo, int pageSize, String sortBy, Long id);

    Page<OfferDTO> getOffers(int pageNo, int pageSize, String sortBy);

    Page<OfferDTO> searchOffers(int pageNo, int pageSize, String sortBy, SearchServiceModel model, String username);

    OfferSellerViewModel getOfferSeller(Long offerId);

    void saveOfferQuestion(OfferQuestionServiceModel model, String username);

    EditOfferViewModel getOfferDetails(Long offerId, String username);

    void removeSpecification(Long specificationId);

    boolean isSpecOwner(String username, Long specificationId);

    void removeOption(Long optionId);

    boolean isOptionOwner(String username, Long optionId);

    void editOffer(EditOfferServiceModel model);

    void removeOffer(Long offerId);

    boolean isOfferOwner(String username, Long offerId);

    List<FeedbackViewModel> getOfferFeedback(Long offerId);
}
