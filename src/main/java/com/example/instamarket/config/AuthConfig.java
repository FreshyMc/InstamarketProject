package com.example.instamarket.config;

import com.example.instamarket.model.enums.RolesEnum;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class AuthConfig extends WebSecurityConfigurerAdapter {
    private  final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public AuthConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests().
                // with this line we allow access to all static resources
                        antMatchers("/css/**", "/js/**", "/fontawesome-free/**", "/img/**", "/bootstrap/**").permitAll().
                    //requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll().
                // the next line allows access to the home page, login page and registration for everyone
                        antMatchers("/", "/login", "/register").permitAll().
                // we permit the page below only for admin users
                        antMatchers("/admin/**").hasRole(RolesEnum.ADMIN.name()).
                // we permit the page below only for approved sellers
                        antMatchers("/offers/add").hasAnyRole(RolesEnum.SELLER.name(), RolesEnum.ADMIN.name()).
                // next we forbid all other pages for unauthenticated users.
                        antMatchers("/**").authenticated().
                and().
                // configure login with login HTML form with two input fileds
                        formLogin().
                // our login page is located at http://<serveraddress>>:<port>/users/login
                        loginPage("/login").
                // this is the name of the <input..> in the login form where the user enters her email/username/etc
                // the value of this input will be presented to our User details service
                // those that want to name the input field differently, e.g. email may change the value below
                        usernameParameter("username").
                // the name of the <input...> HTML filed that keeps the password
                        passwordParameter("password").
                // The place where we should land in case that the login is successful
                        defaultSuccessUrl("/home").
                // the place where I should land if the login is NOT successful
                        failureForwardUrl("/login-error").
                and().
                logout().
                // This is the URL which spring will implement for me and will log the user out.
                        logoutUrl("/logout").
                // where to go after the logout.
                        logoutSuccessUrl("/").
                // remove the session from server
                        invalidateHttpSession(true).
                //delete the cookie that references my session
                        deleteCookies("JSESSIONID");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.
                userDetailsService(userDetailsService).
                passwordEncoder(passwordEncoder);
    }
}
