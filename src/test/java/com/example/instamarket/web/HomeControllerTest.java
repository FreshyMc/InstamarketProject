package com.example.instamarket.web;

import com.example.instamarket.model.entity.Address;
import com.example.instamarket.model.entity.ProfilePicture;
import com.example.instamarket.model.entity.Role;
import com.example.instamarket.model.entity.User;
import com.example.instamarket.model.enums.RolesEnum;
import com.example.instamarket.repository.ProfilePictureRepository;
import com.example.instamarket.repository.RoleRepository;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class HomeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ProfilePictureRepository profilePictureRepository;

    private static final String USERNAME = "tseko";

    private static final String PASSWORD = "test";

    private InstamarketUser instamarketUser;

    private User testUser;

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
        userRepository.deleteAll();
    }

    @Test
    void testHomePage() throws Exception {
        mockMvc.perform(
                get("/home").with(user(instamarketUser))
        ).andExpect(status().isOk()).
                andExpect(model().attributeExists("categories")).
                andExpect(view().name("home"));
    }

    @Test
    void testGoToHomePage() throws Exception {
        mockMvc.perform(
                get("/").with(user(instamarketUser))
        ).andExpect(status().is3xxRedirection());
    }

    @Test
    void testGoToIndexPage() throws Exception {
        mockMvc.perform(
                get("/home")
        ).andExpect(status().is3xxRedirection());
    }
}