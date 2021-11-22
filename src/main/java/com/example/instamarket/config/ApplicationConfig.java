package com.example.instamarket.config;

import com.example.instamarket.misc.AuthHelper;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@Configuration
public class ApplicationConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new Pbkdf2PasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper mapper = new ModelMapper();

        Converter<BigDecimal, String> converter = new Converter<>() {
            @Override
            public String convert(MappingContext<BigDecimal, String> mappingContext) {
                if(mappingContext.getSource() == null){
                    return null;
                }

                DecimalFormat decimalFormat = new DecimalFormat("###,###,###.##");

                return decimalFormat.format(mappingContext.getSource());
            }
        };

        mapper.addConverter(converter);

        return mapper;
    }

    @Bean
    public AuthHelper authHelper(){
        return new AuthHelper();
    }
}
