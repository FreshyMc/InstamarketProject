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
    private List<User> subscribers;

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

    @OneToMany
    public List<User> getSubscribers() {
        return subscribers;
    }

    public Subscriber setSubscribers(List<User> subscribers) {
        this.subscribers = subscribers;
        return this;
    }
}
