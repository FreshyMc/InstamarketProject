package com.example.instamarket.web;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.example.instamarket.model.entity.*;
import com.example.instamarket.model.enums.CategoriesEnum;
import com.example.instamarket.model.enums.RolesEnum;
import com.example.instamarket.repository.*;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class OfferControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProfilePictureRepository profilePictureRepository;

    @Autowired
    private OfferPropertyRepository offerPropertyRepository;

    @Autowired
    private OfferOptionRepository offerOptionRepository;

    private User testUser;

    private static final String USERNAME = "tseko";

    private static final String PASSWORD = "test";

    private static final String OFFER_TITLE = "Demo";

    private static final String OFFER_PRICE = "150";

    private static final String OFFER_DESC = "Demo offer desc";

    private static final CategoriesEnum CATEGORY = CategoriesEnum.VEHICLE;

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
        offerOptionRepository.deleteAll();
        offerPropertyRepository.deleteAll();
        offerRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testAddOfferPage() throws Exception {
        mockMvc.perform(
            get("/offers/add").with(user(instamarketUser))
        ).
                andExpect(status().isOk()).
                andExpect(model().attributeExists("categories")).
                andExpect(view().name("add-offer"));
    }

    @Test
    void testAddOffer() throws Exception {
        mockMvc.perform(
                post("/offers/add").
                        with(user(instamarketUser)).
                        param("title", OFFER_TITLE).
                        param("price", OFFER_PRICE).
                        param("description", OFFER_DESC).
                        param("offerCategory", CATEGORY.name()).
                        with(csrf()).
                        contentType(MediaType.APPLICATION_FORM_URLENCODED)
        ).andExpect(status().is3xxRedirection());

        Assertions.assertEquals(1, offerRepository.count());
    }

    @Test
    void testEditOfferPage() throws Exception {
        Offer offer = initOffer();

        mockMvc.perform(
                get("/offers/" + offer.getId() + "/edit").
                        with(user(instamarketUser))
        ).
                andExpect(status().isOk()).
                andExpect(model().attributeExists("editOfferBindingModel")).
                andExpect(model().attributeExists("offerDetails")).
                andExpect(model().attributeExists("categories")).
                andExpect(view().name("edit-offer"));
    }

    @Test
    void testOfferDetailsPage() throws Exception {
        Offer offer = initOffer();

        mockMvc.perform(
                get("/offers/" + offer.getId() + "/details").
                        with(user(instamarketUser))
        ).
                andExpect(status().isOk()).
                andExpect(model().attributeExists("offer")).
                andExpect(model().attributeExists("sellerInfo")).
                andExpect(model().attributeExists("isSubscriber")).
                andExpect(model().attributeExists("feedback")).
                andExpect(model().attributeExists("feedbackPercent")).
                andExpect(view().name("offer"));
    }

    @Test
    void testRemoveOffer() throws Exception {
        Offer offer = initOffer();

        mockMvc.perform(
                get("/offers/" + offer.getId() + "/remove").with(user(instamarketUser))
        ).
                andExpect(status().is3xxRedirection());

        Assertions.assertTrue(offerRepository.findById(offer.getId()).get().isDeleted());
    }

    @Test
    void testRemoveOption() throws Exception {
        Offer offer = initOfferWithExtras();

        List<Long> propertyId = offer.getOfferProperties().stream().map(o -> {
            return o.getId();
        }).collect(Collectors.toList());

        mockMvc.perform(
                get("/offers/remove/option/" + propertyId.get(0)).with(user(instamarketUser))
        ).andExpect(status().isOk());

        Assertions.assertTrue(offerOptionRepository.findById(propertyId.get(0)).get().isRemoved());
    }

    @Test
    void testRemoveSpecification() throws Exception {
        Offer offer = initOfferWithExtras();

        List<Long> specificationId = offer.getOfferProperties().stream().map(o -> {
            return o.getId();
        }).collect(Collectors.toList());

        mockMvc.perform(
                get("/offers/remove/specification/" + specificationId.get(0)).with(user(instamarketUser))
        ).andExpect(status().isOk());

        Assertions.assertTrue(offerPropertyRepository.findById(specificationId.get(0)).get().isRemoved());
    }

    private Offer initOffer(){
        Offer offer = new Offer();

        Category category = categoryRepository.findCategoryByCategory(CATEGORY).get();

        offer.
                setTitle(OFFER_TITLE).
                setDescription(OFFER_DESC).
                setPrice(new BigDecimal(OFFER_PRICE)).
                setOfferCategory(category).
                setSeller(this.testUser);

        Offer saved = offerRepository.save(offer);

        return saved;
    }

    private Offer initOfferWithExtras(){
        Offer offer = new Offer();

        Category category = categoryRepository.findCategoryByCategory(CATEGORY).get();

        OfferProperty offerProperty = new OfferProperty();

        offerProperty.setPropertyValue("Demo").setPropertyName("Demo").setOffer(offer);

        OfferOption offerOption = new OfferOption();

        offerOption.setOptionValue("Demo").setOptionName("Demo").setOffer(offer);

        offer.
                setTitle(OFFER_TITLE).
                setDescription(OFFER_DESC).
                setPrice(new BigDecimal(OFFER_PRICE)).
                setOfferCategory(category).
                setOfferOptions(List.of(offerOption)).
                setOfferProperties(Set.of(offerProperty)).
                setSeller(this.testUser);

        Offer saved = offerRepository.save(offer);

        offerPropertyRepository.save(offerProperty);

        offerOptionRepository.save(offerOption);

        return saved;
    }
}