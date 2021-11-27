package com.example.instamarket.model.entity;

import com.example.instamarket.model.enums.RolesEnum;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity{
    @Enumerated(value = EnumType.STRING)
    private RolesEnum name;

    public Role() {
    }

    public RolesEnum getName() {
        return name;
    }

    public Role setName(RolesEnum name) {
        this.name = name;
        return this;
    }
}
