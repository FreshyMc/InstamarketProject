package com.example.instamarket.service.impl;

import com.example.instamarket.exception.UserNotFoundException;
import com.example.instamarket.model.entity.Role;
import com.example.instamarket.model.entity.User;
import com.example.instamarket.model.enums.RolesEnum;
import com.example.instamarket.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class InstamarketUserServiceImplTest {
    private User testUser;
    private Role userRole, sellerRole, adminRole;

    private InstamarketUserServiceImpl serviceToTest;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void init(){
        serviceToTest = new InstamarketUserServiceImpl(userRepository);

        userRole = new Role().setName(RolesEnum.USER);
        sellerRole = new Role().setName(RolesEnum.SELLER);
        adminRole = new Role().setName(RolesEnum.ADMIN);

        testUser = new User().setPassword("12345678").setEmail("tseko@abv.bg").setUsername("tseko").setFirstName("Tseko").setLastName("Tsekov").setRoles(Set.of(userRole, sellerRole, adminRole));
    }

    @Test
    public void testUserNotFound(){
        Assertions.assertThrows(UsernameNotFoundException.class,() -> serviceToTest.loadUserByUsername("not_existing_username"));
    }

    @Test
    public void testUserFound(){
        Mockito.when(userRepository.findByUsername(this.testUser.getUsername())).thenReturn(Optional.of(this.testUser));

        UserDetails userDetails = serviceToTest.loadUserByUsername(this.testUser.getUsername());

        String expectedRoles = "ROLE_ADMIN, ROLE_SELLER, ROLE_USER";

        String actualRoles = userDetails.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.joining(", "));

        Assertions.assertEquals(this.testUser.getUsername(), userDetails.getUsername());
        Assertions.assertEquals(expectedRoles, actualRoles);
    }
}
