package com.example.instamarket.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "contacts")
public class Contact extends BaseEntity{
    @ManyToOne
    private User user;
    @OneToMany
    private List<User> contacts;

    public Contact() {
    }

    public User getUser() {
        return user;
    }

    public Contact setUser(User user) {
        this.user = user;
        return this;
    }

    public List<User> getContacts() {
        return contacts;
    }

    public Contact setContacts(List<User> contacts) {
        this.contacts = contacts;
        return this;
    }
}
