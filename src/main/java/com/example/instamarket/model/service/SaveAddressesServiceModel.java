package com.example.instamarket.model.service;

import com.example.instamarket.model.binding.AddressBindingModel;

import java.util.List;

public class SaveAddressesServiceModel {
    private List<AddressBindingModel> newAddress;

    public SaveAddressesServiceModel() {
    }

    public List<AddressBindingModel> getNewAddress() {
        return newAddress;
    }

    public SaveAddressesServiceModel setNewAddress(List<AddressBindingModel> newAddress) {
        this.newAddress = newAddress;
        return this;
    }
}
