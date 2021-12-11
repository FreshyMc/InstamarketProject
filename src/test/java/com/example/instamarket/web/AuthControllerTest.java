package com.example.instamarket.web;

import com.example.instamarket.model.entity.Address;
import com.example.instamarket.model.entity.ProfilePicture;
import com.example.instamarket.model.entity.Role;
import com.example.instamarket.model.entity.User;
import com.example.instamarket.model.enums.RolesEnum;
import com.example.instamarket.repository.AddressRepository;
import com.example.instamarket.repository.ProfilePictureRepository;
import com.example.instamarket.repository.RoleRepository;
import com.example.instamarket.repository.UserRepository;
import com.example.instamarket.service.impl.InstamarketUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@SpringBootTest
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ProfilePictureRepository profilePictureRepository;

    @Autowired
    private AddressRepository addressRepository;

    private static final String USERNAME = "tseko";

    private static final String NEW_USERNAME = "tseko_2";

    private static final String EMAIL = "tseko@abv.bg";

    private static final String NEW_EMAIL = "tseko_2@abv.bg";

    private static final String FIRST_NAME = "Tseko";

    private static final String LAST_NAME = "Tsekov";

    private static final String PASSWORD = "123456789";

    private static final String COUNTRY = "Bulgaria";

    private static final String POSTAL_CODE = "3000";

    private static final String CITY = "Vratsa";

    private static final String STREET = "bul. Ivan Trichkov N16";

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
        addressRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testLoginPage() throws Exception {
        mockMvc.perform(
                get("/login")
        ).
                andExpect(status().isOk()).
                andExpect(view().name("login"));
    }

    @Test
    void testLoginPageWithLoggedInUser() throws Exception {
        mockMvc.perform(
                get("/login").with(user(instamarketUser))
        ).
                andExpect(status().is3xxRedirection());
    }

    @Test
    void testRegisterPage() throws Exception {
        mockMvc.perform(
                get("/register")
        ).
                andExpect(status().isOk()).
                andExpect(view().name("register"));
    }

    @Test
    void testRegisterPageWithLoggedInUser() throws Exception {
        mockMvc.perform(
                get("/register").with(user(instamarketUser))
        ).
                andExpect(status().is3xxRedirection());
    }

    @Test
    void testRegistration() throws Exception {
        mockMvc.perform(
                post("/register").
                        param("username", NEW_USERNAME).
                        param("firstName", FIRST_NAME).
                        param("lastName", LAST_NAME).
                        param("password", PASSWORD).
                        param("confirmPassword", PASSWORD).
                        param("email", NEW_EMAIL).
                        param("country", COUNTRY).
                        param("postalCode", POSTAL_CODE).
                        param("city", CITY).
                        param("street", STREET).
                        with(csrf()).
                        contentType(MediaType.APPLICATION_FORM_URLENCODED)
        ).andExpect(status().is3xxRedirection());

        Assertions.assertEquals(2, userRepository.count());

        Optional<User> user = userRepository.findByUsername(NEW_USERNAME);

        Assertions.assertTrue(user.isPresent());

        User newUser = user.get();

        List<Address> addresses = newUser.getAddresses();

        Assertions.assertFalse(addresses.isEmpty());

        Assertions.assertEquals(FIRST_NAME, newUser.getFirstName());
        Assertions.assertEquals(LAST_NAME, newUser.getLastName());
        Assertions.assertEquals(NEW_EMAIL, newUser.getEmail());

        Assertions.assertEquals(COUNTRY, addresses.get(0).getCountry());
        Assertions.assertEquals(POSTAL_CODE, addresses.get(0).getPostalCode());
        Assertions.assertEquals(CITY, addresses.get(0).getCity());
        Assertions.assertEquals(STREET, addresses.get(0).getStreet());
    }
}