package com.volia.eadmin.config.security;

import org.springframework.security.authentication.AuthenticationManager;

public abstract class BaseTokenAuthenticationFilter extends BaseAuthenticationFilter {

    public BaseTokenAuthenticationFilter(final AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }
}

