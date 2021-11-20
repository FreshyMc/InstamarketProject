package com.example.instamarket.web;

import com.example.instamarket.model.binding.SearchOfferBindingModel;
import com.example.instamarket.model.dto.OfferDTO;
import com.example.instamarket.model.service.SearchServiceModel;
import com.example.instamarket.service.OfferService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/offers")
public class OfferRestController {
    private final OfferService offerService;
    private final ModelMapper modelMapper;

    public OfferRestController(OfferService offerService, ModelMapper modelMapper) {
        this.offerService = offerService;
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
            @RequestBody @Valid SearchOfferBindingModel searchModel){

        SearchServiceModel model = modelMapper.map(searchModel, SearchServiceModel.class);

        return ResponseEntity.ok(offerService.searchOffers(pageNo, pageSize, sortBy, model));
    }
}
