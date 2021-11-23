package com.example.instamarket.model.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "subscribers")
public class Subscriber extends BaseEntity {
    private User seller;
    private User subscriber;
    private boolean isSubscribed;

    public Subscriber() {
    }

    @ManyToOne
    public User getSeller() {
        return seller;
    }

    public Subscriber setSeller(User seller) {
        this.seller = seller;
        return this;
    }

    @ManyToOne
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
