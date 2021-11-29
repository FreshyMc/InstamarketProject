package com.example.instamarket.service.impl;

import com.example.instamarket.model.entity.User;
import com.example.instamarket.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstamarketUserServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public InstamarketUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User with username " + username + "not found!"));

        return mapUserEntityToUserDetails(user);
    }

    private static UserDetails mapUserEntityToUserDetails(User user){
        List<GrantedAuthority> authorities = user.getRoles().stream().map(r -> {
            return new SimpleGrantedAuthority("ROLE_".concat(r.getName().name()));
        }).collect(Collectors.toList());

        Long userId = user.getId();

        String userFullname = String.format("%s %s", user.getFirstName(), user.getLastName());

        return new InstamarketUser(user.getUsername(), user.getPassword(), authorities, userId, userFullname);
    }
}
