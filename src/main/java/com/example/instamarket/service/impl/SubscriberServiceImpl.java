package com.example.instamarket.service.impl;

import com.example.instamarket.exception.UserNotFoundException;
import com.example.instamarket.model.entity.Subscriber;
import com.example.instamarket.model.entity.User;
import com.example.instamarket.model.view.SubscriberViewModel;
import com.example.instamarket.repository.SubscriberRepository;
import com.example.instamarket.repository.UserRepository;
import com.example.instamarket.service.SubscriberService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubscriberServiceImpl implements SubscriberService {
    private final SubscriberRepository subscriberRepository;
    private final UserRepository userRepository;

    public SubscriberServiceImpl(SubscriberRepository subscriberRepository, UserRepository userRepository) {
        this.subscriberRepository = subscriberRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void subscribe(Long sellerId, String username) {
        if(isSubscribed(sellerId, username)){
            return;
        }

        User seller = userRepository.findById(sellerId).orElseThrow(() -> new UserNotFoundException());
        User subscriber = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException());

        Subscriber subscription = subscriberRepository.findBySubscriberAndSeller(subscriber, seller).orElse(null);

        if(subscription != null){
            subscription.setSubscribed(true);

            subscriberRepository.save(subscription);

            return;
        }

        subscription = new Subscriber();

        subscription.setSubscriber(subscriber).setSeller(seller).setSubscribed(true);

        subscriberRepository.save(subscription);
    }

    @Override
    public boolean isSubscribed(Long sellerId, String username) {
        User seller = userRepository.findById(sellerId).orElseThrow(() -> new UserNotFoundException());
        User subscriber = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException());

        Optional<Subscriber> subscription = subscriberRepository.findBySubscriberAndSeller(subscriber, seller);

        if(subscription.isPresent()){
            return subscription.get().isSubscribed();
        }else{
            return false;
        }
    }

    @Override
    public void unsubscribe(Long sellerId, String username) {
        if(!isSubscribed(sellerId, username)){
            return;
        }

        User seller = userRepository.findById(sellerId).orElseThrow(() -> new UserNotFoundException());
        User subscriber = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException());

        //TODO Generic custom exception object for error handling
        Subscriber subscription = subscriberRepository.findBySubscriberAndSeller(subscriber, seller).orElseThrow().setSubscribed(false);

        subscriberRepository.save(subscription);
    }

    @Override
    public List<SubscriberViewModel> listSubscribers(String username) {
        User seller = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException());

        List<SubscriberViewModel> subscribers = subscriberRepository.findAllSubscribers(seller).stream().map(subscriber -> {
            SubscriberViewModel mappedSubscriber = new SubscriberViewModel();

            mappedSubscriber.setSubscriberId(subscriber.getSubscriber().getId());
            mappedSubscriber.setUsername(subscriber.getSubscriber().getUsername());
            mappedSubscriber.setProfilePictureUrl(subscriber.getSubscriber().getProfilePicture().getUrl());

            return mappedSubscriber;
        }).collect(Collectors.toList());

        return subscribers;
    }
}
