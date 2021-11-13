package com.example.instamarket.model.entity;

import com.example.instamarket.model.enums.RolesEnum;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity{
    private RolesEnum name;

    public Role() {
    }

    @Enumerated(value = EnumType.STRING)
    public RolesEnum getName() {
        return name;
    }

    public Role setName(RolesEnum name) {
        this.name = name;
        return this;
    }
}
