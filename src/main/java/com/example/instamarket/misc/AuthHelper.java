package com.example.instamarket.misc;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthHelper {
    public AuthHelper() {
    }

    public boolean isLoggedIn(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth != null && !(auth instanceof AnonymousAuthenticationToken)){
            return true;
        }

        return false;
    }
}
