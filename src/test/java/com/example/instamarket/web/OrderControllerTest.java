package com.example.instamarket.web;

import com.example.instamarket.model.entity.*;
import com.example.instamarket.model.enums.CategoriesEnum;
import com.example.instamarket.model.enums.OrderStatusEnum;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class OrderControllerTest {
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
    private OrderRepository orderRepository;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    private User testUser;

    private static final String USERNAME = "tseko";

    private static final String PASSWORD = "test";

    private static final String OFFER_TITLE = "Demo";

    private static final String OFFER_PRICE = "150";

    private static final String OFFER_DESC = "Demo offer desc";

    private static final CategoriesEnum CATEGORY = CategoriesEnum.VEHICLE;

    private InstamarketUser instamarketUser;

    @BeforeEach
    void setUp() {
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
    void tearDown() {
        orderRepository.deleteAll();
        offerRepository.deleteAll();
        userRepository.deleteAll();
        profilePictureRepository.deleteAll();
    }

    @Test
    void testOrdersPage() throws Exception {
        initOrder();

        mockMvc.perform(
                get("/orders").with(user(instamarketUser))
        ).
                andExpect(status().isOk()).
                andExpect(model().attributeExists("recentOrders")).
                andExpect(model().attributeExists("completedOrders")).
                andExpect(view().name("orders"));
    }

    @Test
    void testCompleteOrder() throws Exception {
        Order order = initOrder();

        mockMvc.perform(
                get("/orders/" + order.getId() + "/received").with(user(instamarketUser))
        ).
                andExpect(status().is3xxRedirection());

        Optional<Order> updatedOrder = orderRepository.findById(order.getId());

        Assertions.assertTrue(updatedOrder.isPresent());

        Order updated = updatedOrder.get();

        OrderStatus completedStatus = orderStatusRepository.findOrderStatusByName(OrderStatusEnum.COMPLETED).get();

        Assertions.assertEquals(completedStatus.getName().name(), updated.getStatus().getName().name());
    }

    @Test
    void testSellerOrdersPage() throws Exception {
        initOrder();

        mockMvc.perform(
                get("/seller/orders").with(user(instamarketUser))
        ).
                andExpect(status().isOk()).
                andExpect(model().attributeExists("recentOrders")).
                andExpect(model().attributeExists("acceptedOrders")).
                andExpect(model().attributeExists("completedOrders")).
                andExpect(view().name("seller/orders"));
    }

    @Test
    void testAcceptOrder() throws Exception {
        Order order = initOrder();

        mockMvc.perform(
                get("/seller/orders/" +  order.getId() + "/accept").with(user(instamarketUser))
        ).
                andExpect(status().is3xxRedirection());

        Optional<Order> acceptedOrder = orderRepository.findById(order.getId());

        Assertions.assertTrue(acceptedOrder.isPresent());

        Order accepted = acceptedOrder.get();

        OrderStatus acceptedStatus = orderStatusRepository.findOrderStatusByName(OrderStatusEnum.ACCEPTED).get();

        Assertions.assertEquals(acceptedStatus.getName().name(), accepted.getStatus().getName().name());
    }

    @Test
    void testCancelOrder() throws Exception {
        Order order = initOrder();

        mockMvc.perform(
                get("/seller/orders/" +  order.getId() + "/cancel").with(user(instamarketUser))
        ).
                andExpect(status().is3xxRedirection());

        Optional<Order> acceptedOrder = orderRepository.findById(order.getId());

        Assertions.assertTrue(acceptedOrder.isPresent());

        Order accepted = acceptedOrder.get();

        OrderStatus acceptedStatus = orderStatusRepository.findOrderStatusByName(OrderStatusEnum.CANCELED).get();

        Assertions.assertEquals(acceptedStatus.getName().name(), accepted.getStatus().getName().name());
    }

    @Test
    void testOrderShipped() throws Exception {
        Order order = initAcceptedOrder();

        mockMvc.perform(
                get("/seller/orders/" +  order.getId() + "/ship").with(user(instamarketUser))
        ).
                andExpect(status().is3xxRedirection());

        Optional<Order> acceptedOrder = orderRepository.findById(order.getId());

        Assertions.assertTrue(acceptedOrder.isPresent());

        Order accepted = acceptedOrder.get();

        OrderStatus shippedStatus = orderStatusRepository.findOrderStatusByName(OrderStatusEnum.SHIPPED).get();

        Assertions.assertEquals(shippedStatus.getName().name(), accepted.getStatus().getName().name());
    }

    private Offer initOffer(){
        Offer offer = new Offer();

        Category category = categoryRepository.findCategoryByCategory(CATEGORY).get();

        offer.
                setTitle(OFFER_TITLE).
                setDescription(OFFER_DESC).
                setPrice(new BigDecimal(OFFER_PRICE)).
                setOfferCategory(category).
                setOfferOptions(null).
                setOfferProperties(null).
                setSeller(this.testUser);

        Offer saved = offerRepository.save(offer);

        return saved;
    }

    private Order initOrder(){
        Order order = new Order();

        OrderStatus waitingStatus = orderStatusRepository.findOrderStatusByName(OrderStatusEnum.WAITING).get();

        order.
                setOffer(this.initOffer()).
                setBuyer(this.testUser).
                setOfferOption(null).
                setStatus(waitingStatus).
                setQuantity(10).
                setTotalPrice(new BigDecimal(1000)).
                setDeliveryAddress(testUser.getAddresses().get(0));

        Order saved = orderRepository.save(order);

        return saved;
    }

    private Order initAcceptedOrder(){
        Order order = new Order();

        OrderStatus waitingStatus = orderStatusRepository.findOrderStatusByName(OrderStatusEnum.ACCEPTED).get();

        order.
                setOffer(this.initOffer()).
                setBuyer(this.testUser).
                setOfferOption(null).
                setStatus(waitingStatus).
                setQuantity(10).
                setTotalPrice(new BigDecimal(1000)).
                setDeliveryAddress(testUser.getAddresses().get(0));

        Order saved = orderRepository.save(order);

        return saved;
    }
}