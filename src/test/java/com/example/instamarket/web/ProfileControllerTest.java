package com.example.instamarket.web;

import com.example.instamarket.model.entity.Address;
import com.example.instamarket.model.entity.ProfilePicture;
import com.example.instamarket.model.entity.Role;
import com.example.instamarket.model.entity.User;
import com.example.instamarket.model.enums.RolesEnum;
import com.example.instamarket.repository.ProfilePictureRepository;
import com.example.instamarket.repository.RoleRepository;
import com.example.instamarket.repository.SellerRequestRepository;
import com.example.instamarket.repository.UserRepository;
import com.example.instamarket.service.impl.InstamarketUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class ProfileControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ProfilePictureRepository profilePictureRepository;

    @Autowired
    private SellerRequestRepository sellerRequestRepository;

    private User testUser;

    private InstamarketUser instamarketUser;

    private static final String USERNAME = "tseko";

    private static final String PASSWORD = "test";

    @BeforeEach
    public void setup() {
        User user = new User();

        Address address = new Address();

        address.setStreet("bul. Hristo Botev N48").setCity("Vratsa").setPostalCode("3000").setCountry("Bulgaria");

        ProfilePicture profilePicture = new ProfilePicture();

        profilePicture.setDefault(false).setUrl("test.jpg");

        profilePictureRepository.save(profilePicture);

        Role userRole = roleRepository.findByName(RolesEnum.USER);
        Role sellerRole = roleRepository.findByName(RolesEnum.SELLER);

        user.
                setPassword(PASSWORD).
                setFirstName("Tseko").
                setLastName("Tsekov").
                setUsername(USERNAME).
                setEmail("tseko@abv.bg").
                setRoles(Set.of(userRole, sellerRole)).
                setAddresses(List.of(address)).
                setProfilePicture(profilePicture);

        testUser = userRepository.save(user);

        List<GrantedAuthority> authorities = user.getRoles().stream().map(r -> {
            return new SimpleGrantedAuthority("ROLE_".concat(r.getName().name()));
        }).collect(Collectors.toList());

        Long userId = user.getId();

        String userFullname = String.format("%s %s", user.getFirstName(), user.getLastName());

        this.instamarketUser = new InstamarketUser(user.getUsername(), user.getPassword(), authorities, userId, userFullname);
    }

    @AfterEach
    void tearDown(){
        sellerRequestRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testProfilePage() throws Exception {
        mockMvc.perform(
                get("/profile").with(user(instamarketUser))
        ).
                andExpect(status().isOk()).
                andExpect(model().attributeExists("profileInfo")).
                andExpect(model().attributeExists("sellerApplied")).
                andExpect(view().name("profile"));
    }

    @Test
    void testChangeProfileNamesPage() throws Exception {
        mockMvc.perform(
                get("/profile/change/names").with(user(instamarketUser))
        ).
                andExpect(status().isOk()).
                andExpect(model().attributeExists("profileNames")).
                andExpect(view().name("change-names"));
    }

    @Test
    void testChangeProfilePicturePage() throws Exception {
        mockMvc.perform(
                get("/profile/change/picture").with(user(instamarketUser))
        ).
                andExpect(status().isOk()).
                andExpect(view().name("change-picture"));
    }

    @Test
    void testChangeProfilePasswordPage() throws Exception {
        mockMvc.perform(
                get("/profile/change/password").with(user(instamarketUser))
        ).
                andExpect(status().isOk()).
                andExpect(view().name("change-password"));
    }

    @Test
    void testManageProfileAddressesPage() throws Exception {
        mockMvc.perform(
                get("/profile/manage/addresses").with(user(instamarketUser))
        ).
                andExpect(status().isOk()).
                andExpect(model().attributeExists("addresses")).
                andExpect(view().name("manage-addresses"));
    }

    @Test
    void becomeSellerRequest() throws Exception {
        mockMvc.perform(
                get("/profile/become-seller").with(user(instamarketUser))
        ).
                andExpect(status().is3xxRedirection()).
                andExpect(flash().attributeExists("sellerRequest"));
    }

    @Test
    void profileShowcasePage() throws Exception {
        mockMvc.perform(
                get("/profile/" + testUser.getId() + "/showcase").with(user(instamarketUser))
        ).
                andExpect(status().isOk()).
                andExpect(model().attributeExists("profileShowcase")).
                andExpect(model().attributeExists("profileOffers")).
                andExpect(view().name("profile-showcase"));
    }
}