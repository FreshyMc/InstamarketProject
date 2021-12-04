package com.example.instamarket.service.impl;

import com.cloudinary.Cloudinary;
import com.example.instamarket.exception.UserNotFoundException;
import com.example.instamarket.model.binding.ProfilePictureBindingModel;
import com.example.instamarket.model.dto.FavouriteOfferDTO;
import com.example.instamarket.model.entity.*;
import com.example.instamarket.model.enums.RolesEnum;
import com.example.instamarket.model.service.ChangePasswordServiceModel;
import com.example.instamarket.model.service.ProfileNamesServiceModel;
import com.example.instamarket.model.service.SaveAddressesServiceModel;
import com.example.instamarket.model.service.UserRegisterServiceModel;
import com.example.instamarket.model.view.ProfileAddressesViewModel;
import com.example.instamarket.model.view.ProfileNamesViewModel;
import com.example.instamarket.model.view.ProfileViewModel;
import com.example.instamarket.repository.*;
import com.example.instamarket.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final String TEMP_FILE = "temp-file";
    private static final String URL = "url";
    private static final String PUBLIC_ID = "public_id";

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ProfilePictureRepository profilePictureRepository;
    private final WishListRepository wishListRepository;
    private final OfferRepository offerRepository;
    private final SellerRequestRepository sellerRequestRepository;
    private final InstamarketUserServiceImpl instamarketUserService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final Cloudinary cloudinary;

    public UserServiceImpl(RoleRepository roleRepository, UserRepository userRepository, AddressRepository addressRepository, CartRepository cartRepository, ProfilePictureRepository profilePictureRepository, WishListRepository wishListRepository, OfferRepository offerRepository, SellerRequestRepository sellerRequestRepository, InstamarketUserServiceImpl instamarketUserService, PasswordEncoder passwordEncoder, ModelMapper modelMapper, Cloudinary cloudinary) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.profilePictureRepository = profilePictureRepository;
        this.wishListRepository = wishListRepository;
        this.offerRepository = offerRepository;
        this.sellerRequestRepository = sellerRequestRepository;
        this.instamarketUserService = instamarketUserService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.cloudinary = cloudinary;
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

    @Override
    public void registerUser(UserRegisterServiceModel userModel) {
        User user = new User();

        Role role = roleRepository.findByName(RolesEnum.USER);

        Address userAddress = new Address();

        ProfilePicture profilePicture = new ProfilePicture();

        userAddress.
                setCountry(userModel.getCountry()).
                setPostalCode(userModel.getPostalCode()).
                setCity(userModel.getCity()).
                setStreet(userModel.getStreet());

        profilePicture.setUrl("https://res.cloudinary.com/tsetsig/image/upload/v1637943223/default_profile_elxa2z.png");

        profilePictureRepository.save(profilePicture);

        user.
                setUsername(userModel.getUsername()).
                setFirstName(userModel.getFirstName()).
                setLastName(userModel.getLastName()).
                setEmail(userModel.getEmail()).
                setRoles(Set.of(role)).
                setPassword(passwordEncoder.encode(userModel.getPassword())).
                setAddresses(List.of(userAddress)).
                setProfilePicture(profilePicture);

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
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException());

        user.setFirstName(model.getFirstName());
        user.setLastName(model.getLastName());

        userRepository.save(user);
    }

    @Override
    public ProfileNamesViewModel takeUserNames(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException());

        ProfileNamesViewModel model = modelMapper.map(user, ProfileNamesViewModel.class);

        return model;
    }

    @Override
    public List<ProfileAddressesViewModel> takeUserAddresses(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException());

        List<ProfileAddressesViewModel> addresses = user.getAddresses().stream().map(addr -> {
            ProfileAddressesViewModel mappedAddr = modelMapper.map(addr, ProfileAddressesViewModel.class);

            return mappedAddr;
        }).collect(Collectors.toList());

        return addresses;
    }

    @Override
    public void saveNewAddresses(SaveAddressesServiceModel model, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException());

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
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException());

        if(!checkPasswords(user, model.getOldPassword())){
            return false;
        }

        user.setPassword(passwordEncoder.encode(model.getNewPassword()));

        userRepository.save(user);

        return true;
    }

    @Override
    public FavouriteOfferDTO addToWishList(Long offerId, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException());

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

    @Override
    public boolean isEmailAlreadyRegistered(String email) {
        return userRepository.findByEmail(email).isEmpty();
    }

    @Override
    public boolean changeProfilePicture(ProfilePictureBindingModel bindingModel, String username) throws IOException {
        MultipartFile profilePicture = bindingModel.getProfilePicture();

        File tempFile = File.createTempFile(TEMP_FILE, profilePicture.getOriginalFilename());
        profilePicture.transferTo(tempFile);
        try{
            Map<String, String> uploadResult = cloudinary.uploader().upload(tempFile, Map.of());

            String pictureUrl = uploadResult.getOrDefault(URL, null);

            String publicId = uploadResult.getOrDefault(PUBLIC_ID, null);

            if(pictureUrl == null || publicId == null){
                return false;
            }

            //TODO Error
            ProfilePicture userProfilePicture = userRepository.findByUsername(username).orElseThrow().getProfilePicture();

            if(userProfilePicture.isDefault() == false){
                cloudinary.uploader().destroy(userProfilePicture.getPublicId(), Map.of());

                userProfilePicture.setUrl(pictureUrl).setPublicId(publicId);
            }else{
                userProfilePicture.setUrl(pictureUrl).setPublicId(publicId).setDefault(false);
            }

            ProfilePicture saved = profilePictureRepository.save(userProfilePicture);

            return true;
        }catch (Exception ex) {
            return false;
        }finally {
            tempFile.delete();
        }
    }

    @Override
    public String getProfilePicture(String username) {
        return userRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException()).getProfilePicture().getUrl();
    }

    @Override
    public void becomeSeller(String username) {
        SellerRequest sellerRequest = sellerRequestRepository.findSellerRequestBySeller_Username(username).orElse(null);

        if(sellerRequest != null){
            return;
        }

        User user = userRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException());

        SellerRequest newRequest = new SellerRequest();

        newRequest.setSeller(user);

        sellerRequestRepository.save(newRequest);
    }

    @Override
    public boolean hasAppliedToBecomeSeller(String username) {
        Optional<SellerRequest> sellerRequest = sellerRequestRepository.findSellerRequestBySeller_Username(username);

        return sellerRequest.isPresent();
    }

    @Override
    public ProfileViewModel getProfileShowcase(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());

        ProfileViewModel mappedUser = modelMapper.map(user, ProfileViewModel.class);

        mappedUser.setProfilePictureUrl(user.getProfilePicture().getUrl());

        return mappedUser;
    }

    private boolean checkPasswords(User user, String oldPassword){
        String currentPassword = user.getPassword();

        return passwordEncoder.matches(oldPassword, currentPassword);
    }
}
