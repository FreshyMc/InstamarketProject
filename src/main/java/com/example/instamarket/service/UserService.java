package com.example.instamarket.service;

import com.example.instamarket.model.binding.ProfilePictureBindingModel;
import com.example.instamarket.model.dto.FavouriteOfferDTO;
import com.example.instamarket.model.service.ChangePasswordServiceModel;
import com.example.instamarket.model.service.ProfileNamesServiceModel;
import com.example.instamarket.model.service.SaveAddressesServiceModel;
import com.example.instamarket.model.service.UserRegisterServiceModel;
import com.example.instamarket.model.view.ProfileAddressesViewModel;
import com.example.instamarket.model.view.ProfileNamesViewModel;

import java.io.IOException;
import java.util.List;

public interface UserService {
    void initializeRoles();

    boolean isUsernameTaken(String username);

    void registerUser(UserRegisterServiceModel userModel);

    void changeNames(ProfileNamesServiceModel model, String user);

    ProfileNamesViewModel takeUserNames(String username);

    List<ProfileAddressesViewModel> takeUserAddresses(String username);

    void saveNewAddresses(SaveAddressesServiceModel model, String username);

    boolean changeUserPassword(ChangePasswordServiceModel model, String username);

    FavouriteOfferDTO addToWishList(Long offerId, String userIdentifier);

    boolean isEmailAlreadyRegistered(String email);

    boolean changeProfilePicture(ProfilePictureBindingModel bindingModel, String username) throws IOException;
}
