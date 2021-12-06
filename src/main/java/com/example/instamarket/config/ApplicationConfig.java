package com.example.instamarket.config;

import com.cloudinary.Cloudinary;
import com.example.instamarket.misc.AuthHelper;
import com.example.instamarket.model.binding.AddOfferBindingModel;
import com.example.instamarket.model.binding.EditOfferBindingModel;
import com.example.instamarket.model.entity.Order;
import com.example.instamarket.model.service.EditOfferServiceModel;
import com.example.instamarket.model.view.EditOfferViewModel;
import com.example.instamarket.model.view.OrderViewModel;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
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
import java.text.DecimalFormatSymbols;
import java.util.Map;

@Configuration
public class ApplicationConfig {
    private final CloudinaryConfig cloudinaryConfig;

    public ApplicationConfig(CloudinaryConfig cloudinaryConfig) {
        this.cloudinaryConfig = cloudinaryConfig;
    }

    @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary(Map.of(
                "cloud_name", cloudinaryConfig.getCloudName(),
                "api_key", cloudinaryConfig.getApiKey(),
                "api_secret", cloudinaryConfig.getApiSecret()
        ));
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new Pbkdf2PasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper mapper = new ModelMapper();

        //Currently not working strategy
        /*
        mapper.createTypeMap(Order.class, OrderViewModel.class).addMappings(map -> {
            map.skip(OrderViewModel::setOrderStatus);
        });
         */

        mapper.createTypeMap(EditOfferViewModel.class, EditOfferBindingModel.class).addMappings(map -> {
            map.skip(EditOfferBindingModel::setOfferImages);
            map.skip(EditOfferBindingModel::setOptions);
            map.skip(EditOfferBindingModel::setProperties);
        });

        Converter<BigDecimal, String> converter = new Converter<>() {
            @Override
            public String convert(MappingContext<BigDecimal, String> mappingContext) {
                if(mappingContext.getSource() == null){
                    return null;
                }

                DecimalFormatSymbols separator = new DecimalFormatSymbols();

                separator.setDecimalSeparator('.');

                DecimalFormat decimalFormat = new DecimalFormat("###,###,###.00", separator);

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
