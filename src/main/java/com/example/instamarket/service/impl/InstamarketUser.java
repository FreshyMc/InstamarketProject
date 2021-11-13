package com.example.instamarket.service.impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class InstamarketUser extends User {
    private Long userId;
    private String userFullname;

    public InstamarketUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public InstamarketUser(String username, String password, Collection<? extends GrantedAuthority> authorities, Long userId, String userFullname) {
        super(username, password, authorities);
        this.userId = userId;
        this.userFullname = userFullname;
    }

    public Long getUserId(){
        return this.userId;
    }

    public String getUserIdentifier(){
        return this.getUsername();
    }

    public String getUserFullname() {
        return this.userFullname;
    }
}
