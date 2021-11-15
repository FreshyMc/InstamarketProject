package com.example.instamarket.service.impl;

import com.example.instamarket.model.entity.Address;
import com.example.instamarket.repository.AddressRepository;
import com.example.instamarket.service.AddressService;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public void deleteAddress(Long id) {
        addressRepository.deleteAddressById(id);
    }
}
