package com.example.instamarket.web;

import com.example.instamarket.misc.OfferOptionObj;
import com.example.instamarket.model.binding.CheckoutBindingModel;
import com.example.instamarket.model.binding.CheckoutItemBindingModel;
import com.example.instamarket.model.dto.CartDTO;
import com.example.instamarket.model.entity.*;
import com.example.instamarket.model.enums.CategoriesEnum;
import com.example.instamarket.model.enums.OrderStatusEnum;
import com.example.instamarket.model.enums.RolesEnum;
import com.example.instamarket.repository.*;
import com.example.instamarket.service.impl.InstamarketUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class CartRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ProfilePictureRepository profilePictureRepository;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private OfferOptionRepository offerOptionRepository;

    @Autowired
    private OfferPropertyRepository offerPropertyRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    private ObjectMapper objectMapper;

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
        cartRepository.deleteAll();
        orderRepository.deleteAll();
        offerRepository.deleteAll();
        userRepository.deleteAll();
        profilePictureRepository.deleteAll();
    }

    @Test
    void testAddToCart() throws Exception {
        Offer offer = initOffer();

        OfferOptionObj option = new OfferOptionObj().setOfferOptionIndex(0);

        mockMvc.perform(
                post("/api/cart/" + offer.getId() + "/add").
                        with(user(instamarketUser)).
                        contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(option))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        ).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.added").value(true));
    }

    @Test
    void testRemoveFromCart() throws Exception {
        Offer offer = initOffer();

        Cart cartItem = initCartItem(offer);

        mockMvc.perform(
                post("/api/cart/" + cartItem.getId() + "/remove").
                        with(user(instamarketUser))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.added").value(true));
    }

    @Test
    void testCartCheckout() throws Exception {
        Offer offer = initOffer();

        Cart cartItem = initCartItem(offer);

        CheckoutBindingModel model = new CheckoutBindingModel();

        CheckoutItemBindingModel itemModel = new CheckoutItemBindingModel();

        itemModel.setOfferId(offer.getId()).setQuantity(10).setOfferOptionIndex(null);

        model.setDeliveryAddress(this.testUser.getAddresses().get(0).getId()).setCartItems(List.of(itemModel));

        mockMvc.perform(
                post("/api/cart/checkout").
                        with(user(instamarketUser)).
                        contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(model))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        ).andExpect(status().isOk());

        Assertions.assertEquals(1, orderRepository.count());

        OrderStatus orderStatus = orderStatusRepository.findOrderStatusByName(OrderStatusEnum.WAITING).get();

        List<Order> ordersWaiting = orderRepository.findAllByBuyerAndStatus(testUser, orderStatus);

        Assertions.assertFalse(ordersWaiting.isEmpty());

        Order order = ordersWaiting.get(0);

        Assertions.assertEquals(offer.getTitle(), order.getOffer().getTitle());
        Assertions.assertEquals(offer.getDescription(), order.getOffer().getDescription());
        Assertions.assertEquals(USERNAME, order.getOffer().getSeller().getUsername());
    }

    private Offer initOffer(){
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

    private Cart initCartItem(Offer offer){
        Cart cart = new Cart();

        cart.setOffer(offer).setOfferOption(null).setBuyer(this.testUser).setRemoved(false);

        Cart saved = cartRepository.save(cart);

        return saved;
    }
}