package com.volia.eadmin.config.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class BaseAuthenticationManager implements AuthenticationManager {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return authentication instanceof TokenAuthentication ? authentication : setAuthenticated(authentication);
    }

    private Authentication setAuthenticated(Authentication authentication){
        authentication.setAuthenticated(false);
        return authentication;
    }
}
