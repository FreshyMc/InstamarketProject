package com.example.instamarket.model.entity;

import com.example.instamarket.model.enums.ShippingTypesEnum;

import javax.persistence.*;

@Entity
@Table(name = "shipping")
public class Shipping extends BaseEntity{
    @Enumerated(EnumType.STRING)
    private ShippingTypesEnum shipping;
    @Column(nullable = false)
    private String displayName;

    public Shipping() {
    }

    public ShippingTypesEnum getShipping() {
        return shipping;
    }

    public Shipping setShipping(ShippingTypesEnum shipping) {
        this.shipping = shipping;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Shipping setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }
}
