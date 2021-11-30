package com.example.instamarket.service.impl;

import com.example.instamarket.exception.UserNotFoundException;
import com.example.instamarket.model.entity.Address;
import com.example.instamarket.model.entity.User;
import com.example.instamarket.model.view.ProfileAddressesViewModel;
import com.example.instamarket.repository.AddressRepository;
import com.example.instamarket.repository.UserRepository;
import com.example.instamarket.service.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public AddressServiceImpl(AddressRepository addressRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void deleteAddress(Long id) {
        addressRepository.deleteAddressById(id);
    }

    @Override
    public List<ProfileAddressesViewModel> findAllUserAddresses(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException());

        return addressRepository.findAllByUser(user).stream().map(this::toAddressViewModel).collect(Collectors.toList());
    }

    @Override
    public boolean isOwner(Long id, String username) {
        //TODO Custom Exception
        Address address = addressRepository.findById(id).orElseThrow();

        return address.getUser().getUsername().equals(username);
    }

    private ProfileAddressesViewModel toAddressViewModel(Address address){
        return modelMapper.map(address, ProfileAddressesViewModel.class);
    }
}
