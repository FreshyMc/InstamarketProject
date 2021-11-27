package com.example.instamarket.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "subscribers")
public class Subscriber extends BaseEntity {
    @ManyToOne
    private User seller;
    @ManyToOne
    private User subscriber;
    @Column
    private boolean isSubscribed;

    public Subscriber() {
    }

    public User getSeller() {
        return seller;
    }

    public Subscriber setSeller(User seller) {
        this.seller = seller;
        return this;
    }

    public User getSubscriber() {
        return subscriber;
    }

    public Subscriber setSubscriber(User subscriber) {
        this.subscriber = subscriber;
        return this;
    }

    public boolean isSubscribed() {
        return isSubscribed;
    }

    public Subscriber setSubscribed(boolean subscribed) {
        isSubscribed = subscribed;
        return this;
    }
}
