package com.example.instamarket.web;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.example.instamarket.model.entity.*;
import com.example.instamarket.model.enums.RolesEnum;
import com.example.instamarket.repository.ProfilePictureRepository;
import com.example.instamarket.repository.RoleRepository;
import com.example.instamarket.repository.SubscriberRepository;
import com.example.instamarket.repository.UserRepository;
import com.example.instamarket.service.impl.InstamarketUser;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class SubscriberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private ProfilePictureRepository profilePictureRepository;

    private User testUser;

    private static String OFFER_TITLE = "Demo offer";

    private static String OFFER_DESC = "Demo descripton";

    private static String OFFER_PRICE = "100.00";

    private static final String USERNAME = "tseko";

    private static final String PASSWORD = "test";

    private InstamarketUser instamarketUser;

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
        subscriberRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testSubscribe() throws Exception {
        mockMvc.perform(
                post("/subscribe/" + this.testUser.getId()).param("offerId", "1").
                        with(user(instamarketUser)).with(csrf())
        ).
                andExpect(flash().attributeExists("subscription")).
                andExpect(status().is3xxRedirection());

        Assert.assertEquals(1, subscriberRepository.count());
    }

    @Test
    void testUnsubscribe() throws Exception {
        Subscriber sub = initSubscriber();

        mockMvc.perform(
                post("/unsubscribe/" + sub.getSeller().getId()).param("offerId", "1").
                with(user(instamarketUser)).with(csrf())
        ).
                andExpect(flash().attributeExists("subscription")).
                andExpect(status().is3xxRedirection());

        Assertions.assertEquals(0, subscriberRepository.findAllSubscribers(this.testUser).size());
    }

    @Test
    void testSubscribersPage() throws Exception {
        Subscriber sub = initSubscriber();

        mockMvc.perform(
                get("/subscribers").
                with(user(instamarketUser))
        ).
                andExpect(status().isOk()).
                andExpect(model().attributeExists("subscribers")).
                andExpect(view().name("subscribers"));
    }

    private Subscriber initSubscriber(){
        Subscriber subscriber = new Subscriber();

        subscriber.setSubscribed(true).setSubscriber(this.testUser).setSeller(this.testUser);

        Subscriber saved = subscriberRepository.save(subscriber);

        return saved;
    }
}