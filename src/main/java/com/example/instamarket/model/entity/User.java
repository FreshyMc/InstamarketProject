package com.example.instamarket.model.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity{
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String profilePicture;
    private Set<Role> roles;
    private List<Address> addresses;

    public User() {
    }

    @Column(length = 30, nullable = false, unique = true)
    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    @Column(nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Column(nullable = false)
    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    public Set<Role> getRoles() {
        return roles;
    }

    public User setRoles(Set<Role> role) {
        this.roles = role;
        return this;
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<Address> getAddresses() {
        return addresses;
    }

    public User setAddresses(List<Address> addresses) {
        this.addresses = addresses;
        return this;
    }

    @Column(nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    @Column
    public String getProfilePicture() {
        return profilePicture;
    }

    public User setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }
}
