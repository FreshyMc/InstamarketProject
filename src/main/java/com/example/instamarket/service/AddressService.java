package com.example.instamarket.service;

import com.example.instamarket.model.view.ProfileAddressesViewModel;

import java.util.List;

public interface AddressService {
    void deleteAddress(Long id);

    List<ProfileAddressesViewModel> findAllUserAddresses(String username);
}
