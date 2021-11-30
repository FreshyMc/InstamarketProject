package com.example.instamarket.config;

import com.example.instamarket.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
    @Autowired
    private InstamarketMethodSecurityExpressionHandler instamarketMethodSecurityExpressionHandler;

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return instamarketMethodSecurityExpressionHandler;
    }

    @Bean
    public InstamarketMethodSecurityExpressionHandler createExpressionHandler(AddressService addressService){
        return new InstamarketMethodSecurityExpressionHandler(addressService);
    }
}
