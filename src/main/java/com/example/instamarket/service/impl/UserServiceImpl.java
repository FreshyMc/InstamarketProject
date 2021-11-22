package com.example.instamarket.service.impl;

import com.example.instamarket.model.dto.FavouriteOfferDTO;
import com.example.instamarket.model.entity.*;
import com.example.instamarket.model.enums.RolesEnum;
import com.example.instamarket.model.service.ChangePasswordServiceModel;
import com.example.instamarket.model.service.ProfileNamesServiceModel;
import com.example.instamarket.model.service.SaveAddressesServiceModel;
import com.example.instamarket.model.service.UserRegisterServiceModel;
import com.example.instamarket.model.view.ProfileAddressesViewModel;
import com.example.instamarket.model.view.ProfileNamesViewModel;
import com.example.instamarket.repository.*;
import com.example.instamarket.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final CartRepository cartRepository;
    private final WishListRepository wishListRepository;
    private final OfferRepository offerRepository;
    private final InstamarketUserServiceImpl instamarketUserService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserServiceImpl(RoleRepository roleRepository, UserRepository userRepository, AddressRepository addressRepository, CartRepository cartRepository, WishListRepository wishListRepository, OfferRepository offerRepository, InstamarketUserServiceImpl instamarketUserService, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.cartRepository = cartRepository;
        this.wishListRepository = wishListRepository;
        this.offerRepository = offerRepository;
        this.instamarketUserService = instamarketUserService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public void initializeRoles() {
        if(roleRepository.count() == 0){
            Arrays.stream(RolesEnum.values()).map(enumRole -> {
               Role role = new Role();

               role.setName(enumRole);

               return role;
            }).forEach(roleRepository::save);
        }
    }

    @Override
    public boolean isUsernameTaken(String username) {
        return userRepository.findByUsername(username).isEmpty();
    }

    //TODO Check if email is already registered
    @Override
    public void registerUser(UserRegisterServiceModel userModel) {
        User user = new User();

        Role role = roleRepository.findByName(RolesEnum.USER);

        Address userAddress = new Address();

        userAddress.
                setCountry(userModel.getCountry()).
                setPostalCode(userModel.getPostalCode()).
                setCity(userModel.getCity()).
                setStreet(userModel.getStreet());

        user.
                setUsername(userModel.getUsername()).
                setFirstName(userModel.getFirstName()).
                setLastName(userModel.getLastName()).
                setEmail(userModel.getEmail()).
                setRoles(Set.of(role)).
                setPassword(passwordEncoder.encode(userModel.getPassword())).
                setAddresses(List.of(userAddress));

        user = userRepository.save(user);

        userAddress.setUser(user);

        addressRepository.save(userAddress);

        UserDetails principal = instamarketUserService.loadUserByUsername(user.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal,
                user.getPassword(),
                principal.getAuthorities()
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public void changeNames(ProfileNamesServiceModel model, String username) {
        User user = userRepository.findByUsername(username).orElseThrow();

        user.setFirstName(model.getFirstName());
        user.setLastName(model.getLastName());

        userRepository.save(user);
    }

    @Override
    public ProfileNamesViewModel takeUserNames(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();

        ProfileNamesViewModel model = modelMapper.map(user, ProfileNamesViewModel.class);

        return model;
    }

    @Override
    public List<ProfileAddressesViewModel> takeUserAddresses(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();

        List<ProfileAddressesViewModel> addresses = user.getAddresses().stream().map(addr -> {
            ProfileAddressesViewModel mappedAddr = modelMapper.map(addr, ProfileAddressesViewModel.class);

            return mappedAddr;
        }).collect(Collectors.toList());

        return addresses;
    }

    @Override
    public void saveNewAddresses(SaveAddressesServiceModel model, String username) {
        User user = userRepository.findByUsername(username).orElseThrow();

        List<Address> addresses = user.getAddresses();

        model.getNewAddress().stream().map(addr -> {
            Address newAddr = modelMapper.map(addr, Address.class);

            newAddr.setUser(user);

            return newAddr;
        }).forEach(newAddr -> {
            addresses.add(newAddr);
        });

        user.setAddresses(addresses);

        userRepository.save(user);
    }

    @Override
    public boolean changeUserPassword(ChangePasswordServiceModel model, String username) {
        User user = userRepository.findByUsername(username).orElseThrow();

        if(!checkPasswords(user, model.getOldPassword())){
            return false;
        }

        user.setPassword(passwordEncoder.encode(model.getNewPassword()));

        userRepository.save(user);

        return true;
    }

    @Override
    public FavouriteOfferDTO addToWishList(Long offerId, String username) {
        User user = userRepository.findByUsername(username).orElseThrow();

        WishList isAdded = wishListRepository.findByOfferIdAndUser(offerId, user).orElse(null);

        if(isAdded == null){
            Offer offer = offerRepository.findById(offerId).orElseThrow();

            WishList wishList = new WishList();

            wishList.setUser(user);
            wishList.setOffer(offer);

            wishListRepository.save(wishList);

            FavouriteOfferDTO favouriteOffer = modelMapper.map(offer, FavouriteOfferDTO.class);

            favouriteOffer.setFavourite(true);

            return favouriteOffer;
        }else if(isAdded.isRemoved()){
            isAdded.setRemoved(false);

            wishListRepository.save(isAdded);

            FavouriteOfferDTO favouriteOffer = new FavouriteOfferDTO();

            favouriteOffer.setFavourite(true);

            return favouriteOffer;
        }

        isAdded.setRemoved(true);

        wishListRepository.save(isAdded);

        FavouriteOfferDTO favouriteOffer = new FavouriteOfferDTO();

        favouriteOffer.setFavourite(false);

        return favouriteOffer;
    }

    private boolean checkPasswords(User user, String oldPassword){
        String currentPassword = user.getPassword();

        return passwordEncoder.matches(oldPassword, currentPassword);
    }
}
