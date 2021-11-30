package com.example.instamarket.config;

import com.example.instamarket.service.AddressService;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class OwnerSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {
    private AddressService addressService;
    private Object filterObject;
    private Object returnObject;

    /**
     * Creates a new instance
     *
     * @param authentication the {@link Authentication} to use. Cannot be null.
     */
    public OwnerSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    public boolean isAddressOwner(Long id){
        String username = getCurrentUsername();

        if(username != null){
            return addressService.isOwner(id, username);
        }

        return false;
    }

    public void setAddressService(AddressService addressService){
        this.addressService = addressService;
    }

    public String getCurrentUsername(){
        Authentication auth = getAuthentication();

        if(auth.getPrincipal() instanceof UserDetails){
            return ((UserDetails) auth.getPrincipal()).getUsername();
        }

        return null;
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return returnObject;
    }

    @Override
    public Object getThis() {
        return this;
    }
}
