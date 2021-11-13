package com.example.instamarket.service;

import com.example.instamarket.model.service.ProfileNamesServiceModel;
import com.example.instamarket.model.service.UserRegisterServiceModel;

public interface UserService {
    void initializeRoles();

    boolean isUsernameTaken(String username);

    void registerUser(UserRegisterServiceModel userModel);

    void changeNames(ProfileNamesServiceModel model, String user);
}
