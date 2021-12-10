package com.example.instamarket.web;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.example.instamarket.model.entity.*;
import com.example.instamarket.model.enums.CategoriesEnum;
import com.example.instamarket.model.enums.RolesEnum;
import com.example.instamarket.repository.*;
import com.example.instamarket.service.WishListService;
import com.example.instamarket.service.impl.InstamarketUser;
import com.sun.security.auth.UserPrincipal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class WishListControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private WishListRepository wishListRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RoleRepository roleRepository;

    private User testUser;

    private static String OFFER_TITLE = "Demo offer";

    private static final String USERNAME = "tseko";

    private static final String PASSWORD = "test";

    private InstamarketUser instamarketUser;

    @BeforeEach
    public void setup() {
        User user = new User();

        Address address = new Address();

        address.setStreet("bul. Hristo Botev N48").setCity("Vratsa").setPostalCode("3000").setCountry("Bulgaria");

        Role userRole = roleRepository.findByName(RolesEnum.USER);
        Role sellerRole = roleRepository.findByName(RolesEnum.SELLER);

        user.
                setPassword(PASSWORD).
                setFirstName("Tseko").
                setLastName("Tsekov").
                setUsername(USERNAME).
                setEmail("tseko@abv.bg").
                setRoles(Set.of(userRole, sellerRole)).setAddresses(List.of(address));

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
        orderRepository.deleteAll();
        wishListRepository.deleteAll();
        offerRepository.deleteAll();
        userRepository.deleteAll();
    }

    private Offer initOffer(){
        Offer offer = new Offer();

        Category category = categoryRepository.findCategoryByCategory(CategoriesEnum.VEHICLE).get();

        OfferImage offerImage = new OfferImage().setImageUrl("demo.jpg");

        offer.setOfferCategory(category).
                setPrice(new BigDecimal(100)).
                setImages(Set.of(offerImage)).
                setTitle(OFFER_TITLE).
                setSeller(testUser);

        Offer saved = offerRepository.save(offer);

        return saved;
    }

    private WishList initWishListItem(){
        WishList wishListItem = new WishList();

        wishListItem.setOffer(initOffer()).setUser(testUser);

        WishList saved = wishListRepository.save(wishListItem);

        return saved;
    }

    @Test
    void testWishList() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                get("/wishlist").with(user(instamarketUser))
        ).
                andExpect(status().isOk()).
                andExpect(view().name("wishlist")).
                andExpect(model().attributeExists("wishListItems")).
                andReturn();

        Object items = mvcResult.getModelAndView().getModel().get("wishListItems");
    }
}