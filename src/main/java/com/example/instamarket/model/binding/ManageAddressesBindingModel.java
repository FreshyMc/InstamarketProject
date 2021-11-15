package com.example.instamarket.model.binding;

import javax.validation.Valid;
import java.util.List;

public class ManageAddressesBindingModel {
    @Valid
    private List<AddressBindingModel> newAddress;

    public ManageAddressesBindingModel() {
    }

    public List<AddressBindingModel> getNewAddress() {
        return newAddress;
    }

    public ManageAddressesBindingModel setNewAddress(List<AddressBindingModel> newAddress) {
        this.newAddress = newAddress;
        return this;
    }
}
