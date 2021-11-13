package com.example.instamarket.model.entity;

import com.example.instamarket.model.enums.ShippingTypesEnum;

import javax.persistence.*;

@Entity
@Table(name = "shipping")
public class Shipping extends BaseEntity{
    private ShippingTypesEnum shipping;
    private String displayName;

    public Shipping() {
    }

    @Enumerated(EnumType.STRING)
    public ShippingTypesEnum getShipping() {
        return shipping;
    }

    public Shipping setShipping(ShippingTypesEnum shipping) {
        this.shipping = shipping;
        return this;
    }

    @Column(nullable = false)
    public String getDisplayName() {
        return displayName;
    }

    public Shipping setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }
}
