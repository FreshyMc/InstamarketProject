package com.example.instamarket.web;

import com.example.instamarket.model.dto.OfferDTO;
import com.example.instamarket.service.OfferService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/offers")
public class OfferRestController {
    private final OfferService offerService;

    public OfferRestController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping
    public ResponseEntity<Page<OfferDTO>> getOffers(
            @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "6") Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy){

        return ResponseEntity.ok(offerService.getOffers(pageNo, pageSize, sortBy));
    }
}
