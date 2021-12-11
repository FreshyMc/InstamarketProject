package com.example.instamarket.service.impl;

import com.example.instamarket.model.entity.*;
import com.example.instamarket.model.enums.RolesEnum;
import com.example.instamarket.model.service.OfferQuestionServiceModel;
import com.example.instamarket.model.view.FeedbackViewModel;
import com.example.instamarket.repository.*;
import com.example.instamarket.service.OfferService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@SpringBootTest
public class OrderServiceImplTest {
    @Autowired
    private OfferService offerService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private ProfilePictureRepository profilePictureRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OfferQuestionRepository offerQuestionRepository;

    @MockBean
    private OrderFeedbackRepository orderFeedbackRepository;

    private static final String USERNAME = "Tseko_G";

    @AfterEach
    void tearDown(){
        orderRepository.deleteAll();
        orderFeedbackRepository.deleteAll();
        offerQuestionRepository.deleteAll();
        offerRepository.deleteAll();
        offerQuestionRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testGetOfferFeedback(){
        User user = initUser();

        Offer offer = initOffer(user);

        Order order = initOrder(user, offer);

        initFeedback(order);

        List<FeedbackViewModel> offerFeedback = offerService.getOfferFeedback(offer.getId());
    }

    @Test
    void testOfferQuestion(){
        OfferQuestionServiceModel model = new OfferQuestionServiceModel();

        User user = initUser();
        Offer offer = initOffer(user);

        model.setQuestion("Demo question");
        model.setOfferId(offer.getId());

        offerService.saveOfferQuestion(model, user.getUsername());

        Assertions.assertEquals(1, offerQuestionRepository.count());
    }

    private Order initOrder(User user, Offer offer){
        Order order = new Order().setQuantity(10).setTotalPrice(new BigDecimal(1000)).setOffer(offer).setBuyer(user);

        Order saved = orderRepository.save(order);

        return saved;
    }

    private Offer initOffer(User user){
        Offer offer = new Offer();

        offer.setSeller(user).setPrice(new BigDecimal(100)).setTitle("Demo offer").setDescription("Demo desc");

        Offer saved = offerRepository.save(offer);

        return saved;
    }

    private User initUser(){
        User user = new User();

        Role userRole = roleRepository.findByName(RolesEnum.USER);
        Role sellerRole = roleRepository.findByName(RolesEnum.SELLER);

        ProfilePicture profilePicture = new ProfilePicture().setDefault(false).setUrl("demo.jpg");

        profilePictureRepository.save(profilePicture);

        user.
                setUsername(USERNAME).
                setFirstName("Tseko").
                setPassword("12345678").
                setLastName("Tsekov").
                setEmail("demo@demo.com").
                setProfilePicture(profilePicture).setRoles(Set.of(userRole, sellerRole));

        User saved = userRepository.save(user);

        return saved;
    }

    private OrderFeedback initFeedback(Order order){
        OrderFeedback feedback = new OrderFeedback();

        feedback.setBuyer(order.getBuyer()).setOrder(order).setFeedback("Demo feedback").setRating(3);

        OrderFeedback saved = orderFeedbackRepository.save(feedback);

        return saved;
    }
}
